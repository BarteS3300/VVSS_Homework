package pizzashop.repository;

import pizzashop.model.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MenuRepository {
    private static String filename = "data/menu.txt";
    private List<MenuItem> listMenu = new ArrayList<>();

    public MenuRepository(){
        readMenu();
    }

    private void readMenu(){
        File file = new File(filename);
        try(FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr)) {
            String line = null;
            while((line=br.readLine())!=null){
                MenuItem menuItem = getMenuItem(line);
                listMenu.add(menuItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuItem getMenuItem(String line){
        if (line==null || line.isEmpty()) {
            return null;
        }
        StringTokenizer st=new StringTokenizer(line, ",");
        Integer id = Integer.parseInt(st.nextToken());
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        return new MenuItem(id, name, price);
    }

    public List<MenuItem> getMenu(){
        return listMenu;
    }
}
