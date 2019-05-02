import com.opencsv.CSVReader;
import org.apache.jena.rdf.model.*;
import java.io.*;
import java.util.ArrayList;

class CSVEdgeParser {

    private static String baseURI    = Main.baseURI;
    static void execute(String fileName, String tableName) throws Exception {
        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String[] line;
        CSVReader csvReader = new CSVReader(reader);
        line = csvReader.readNext();
        if (line.length!=2) throw new Exception("Edge CSV should have exactly two (2) columns");

        ArrayList<String> localURIs = new ArrayList<String>();
        for (String elem:line) {
            localURIs.add(baseURI + elem + '/');
        }

        Property predicate = model.createProperty(baseURI + tableName);
        while ((line = csvReader.readNext()) != null) {
            Resource subject =  model.createResource(localURIs.get(0) + line[0]);
            Resource object =  model.createResource(localURIs.get(1) + line[1]);
            subject.addProperty(predicate, object);
        }
        reader.close();
        csvReader.close();

        // now write the model to a file
        Writer writer = new FileWriter(String.format("%s%s.txt", Main.outputPath, tableName));
        model.write(writer, Main.tripleFormat);
        writer.close();
    }
}
