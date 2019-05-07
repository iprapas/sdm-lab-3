import java.lang.*;

public class Main {
    static String baseURI    = "http://localhost:8890/lab3/";
    private static String[] nodeNames = {"Author", "Paper", "JournalVolume", "Review", "Keyword", "Conference", "Journal", "ConferenceEdition"};
    private static String[] edgeNames = { "cites", "relatedToKeyword", "refersTo", "belongsToJournal", "wroteReview", "wrotePaper", "publishedInConference", "belongsToConference", "publishedInJournal", "hasMainAuthor"};
    static String outputPath = "./src/main/resources/output/";
    static String tripleFormat = "NT";
    /*
     * "TURTLE"	TURTLE
     * "TTL"	TURTLE
     * "Turtle"	TURTLE
     * "N-TRIPLES"	NTRIPLES
     * "N-TRIPLE"	NTRIPLES
     * "NT"	NTRIPLES
     * "JSON-LD"	JSONLD
     * "RDF/XML-ABBREV"	RDFXML
     * "RDF/XML"	RDFXML_PLAIN
     * "N3"	N3
     * "RDF/JSON"	RDFJSON
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Wrong number of parameters, usage: load N (number of documents to create) or Q1/Q2");
        }
        String inputPath = "./src/main/resources/input/";
        String filePath;
        if (args[0].equals("nodes")) {
            for (String tableName:nodeNames) {
                filePath = inputPath + tableName + ".csv";
                CSVNodeParser.execute(filePath, tableName);
                System.out.println("Finished conversion of -" + tableName + "- to RDF triples");
            }
        }
        else if (args[0].equals("edges")) {
            for (String tableName:edgeNames) {
                filePath = inputPath + tableName + ".csv";
                CSVEdgeParser.execute(filePath, tableName);
                System.out.printf("Finished conversion of -%s- to RDF triples%n", tableName);
            }
        }
        else {
            throw new Exception("Wrong parameters, usage: populate N (number of documents to create) or Q1/Q2");
        }


    }
}
