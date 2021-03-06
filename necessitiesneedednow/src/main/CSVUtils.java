import java.io.*;
import java.util.*;


public class CSVUtils {

    public String filePath;
    public String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }


    public Boolean createCSV(shoppingList cart) throws IOException {
        File csv = new File(filePath+ "\\" +fileName);
        ArrayList<String> added = new ArrayList<String>(cart.totalAmountOfItem.keySet().size());
        ArrayList<String[]> rows = shoppingCartToStringArray(cart);
        FileWriter writer = new FileWriter(csv);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (String[] row : rows) {
            bufferedWriter.append(String.join(",",row));
            bufferedWriter.append("\n");
            bufferedWriter.flush();
        }
        bufferedWriter.close();
        return true;
    }


    public ArrayList<String[]> shoppingCartToStringArray(shoppingList cart){
        CSVArrayUtils util = new CSVArrayUtils();
        int size = cart.totalAmountOfItem.keySet().size();
        ArrayList<String[]> rows = new ArrayList<String[]>(size);

        Set<Item> items = cart.totalAmountOfItem.keySet();
        String[] productNames = util.itemNamesToStringArray(items);
        Double[] productPrices = util.pricesToDoubleArray(items);
        Integer[] isbnCodes = util.isbnCodeToIntArray(items);
        Boolean[] perishables = util.isPerishableToBoolArray(items);

        String[] locations = util.LocationsToStringArray(cart.itemToStore.values());
        Integer[] quantities = util.quantityToIntegerArray(cart.totalAmountOfItem.values());
        Boolean[] subscriptions = util.subscriptionsToBooleanArray(cart.subscriptions.values());

        int rowsIndex = 0;
        for(int index = 0; index < size; index++){
            String[] row = new String[7];
            row[0] = productNames[index];
            row[1] = isbnCodes[index].toString();
            row[2] = productPrices[index].toString();
            row[3] = perishables[index].toString();
            row[4] = quantities[index].toString();
            row[5] = locations[index];
            row[6] = subscriptions[index].toString();
            rows.add(rowsIndex, row);
            rowsIndex++;
        }
        return rows;
    }

    public String removeSpecialCharacters(String data) {
        String editedString = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            editedString = "\"" + data + "\"";
        }
        return editedString;
    }

    public ArrayList<String[]> removeAllSpecialCharacters(ArrayList<String[]> array){
        ArrayList<String[]> result = new ArrayList<>(array.size());
        for(int arrayIndex = 0; arrayIndex < array.size(); arrayIndex++){
            for(int stringArrayIndex = 0; stringArrayIndex < array.get(arrayIndex).length; stringArrayIndex++) {
                String wordToChange = array.get(arrayIndex)[stringArrayIndex];
                String editedWord = removeSpecialCharacters(wordToChange);
                array.get(arrayIndex)[stringArrayIndex] = editedWord;
            }
        }
        return result;
    }
}