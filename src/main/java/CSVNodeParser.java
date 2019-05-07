import com.opencsv.CSVReader;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import java.io.*;
import java.util.ArrayList;

class CSVNodeParser {
    private static String baseURI    = Main.baseURI;
    static void execute(String fileName, String tableName) throws IOException {
        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix( "base", baseURI );

        String localURI = baseURI + tableName;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] line;
        assert reader != null;
        CSVReader csvReader = new CSVReader(reader);
        line = csvReader.readNext();
        ArrayList<Property> properties = new ArrayList<Property>();
        for (int i=1; i<=line.length-1; i++) {
            properties.add(model.createProperty(baseURI + line[i]));
        }
        Property typeProperty = model.createProperty(localURI);
        while ((line = csvReader.readNext()) != null) {
            Resource res = model.createResource(localURI + "/" + line[0]);
            int i =1;
            for (Property prop:properties) {
                res.addProperty(prop, line[i++]);
            }
            res.addProperty(RDF.type, typeProperty);
        }
        reader.close();
        csvReader.close();

        // now write the model to a file
        Writer writer = new FileWriter(String.format("%sLAB3.%s.nt", Main.outputPath, tableName));
        model.write(writer, Main.tripleFormat);
        writer.close();
    }


}
