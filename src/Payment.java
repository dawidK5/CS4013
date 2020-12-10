public class Payment {
    CSVHandler csv = new CSVHandler();
    Tax tax = new Tax();
    public void makePayment(String eircode){
        String str = csv.readFromProperties(eircode);
        String[] values = str.split(",");
        for (int i = 0; i < values.length; i++) {
            if(i == 1) {
                String marketValue = values[i];
            }else if(i == 2){
                String locationType = values[i];
            }else if(i == 3){
                boolean primaryResidence;
                if(values[i].equals("yes")){
                    primaryResidence = true;
                }else if (values[i].equals("no")){
                    primaryResidence = false;
                }
            }

        }
    }
}
