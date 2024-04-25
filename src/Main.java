import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try{
            File file = new File("C:\\Users\\smgF438\\IdeaProjects\\izpitvane_Hristina_11d\\src\\testData_Apartments.txt");
            Scanner scanner = new Scanner(file);
            List<Apartment> apartments = new ArrayList<>();
            Map<String, Integer> br = new HashMap<>();

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                String city = parts[0];
                int rooms = Integer.parseInt(parts[1]);
                int area = Integer.parseInt(parts[2]);
                int price = Integer.parseInt(parts[3]);
                String phone = parts[4];

                apartments.add(new Apartment(city, rooms, area, price, phone));

                br.put(city, br.getOrDefault(city, 0) + 1);

            }
            scanner.close();

            List<Map.Entry<String, Integer>> brList = new ArrayList<>(br.entrySet());
            brList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            List<String> cities = new ArrayList<>();
            int maxAp = brList.get(0).getValue();
            for(Map.Entry<String, Integer> entry : brList){
                if (entry.getValue() == maxAp){
                    cities.add(entry.getKey());
                }
                else{
                    break;
                }
            }

            List<Apartment> suitableApartments = new ArrayList<>();
            for (Apartment apartment : apartments){
                if (apartment.getRooms() == 3 && apartment.getArea() > 100 && apartment.getCity()!="Пловдив"){
                    suitableApartments.add(apartment);
                }
            }
            if (suitableApartments.isEmpty()){
                throw new UnsuitableApartmentsException("There are no suitable apartments.");
            }
            Collections.sort(suitableApartments);

            Set<String> phoneNumbers = new HashSet<>();
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Apartment apartment : suitableApartments.subList(0, Math.min(5, suitableApartments.size()))){
                if (phoneNumbers.add(apartment.getPhone())){
                    bufferedWriter.write(apartment.getPhone() + "\n");
                }
            }
            bufferedWriter.write(cities.toString());
            bufferedWriter.close();
            System.out.println("Output written to file successfully.");

        }
        catch (IOException | UnsuitableApartmentsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
