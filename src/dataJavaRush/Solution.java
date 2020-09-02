package dataJavaRush;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* 
Читаем и пишем в файл: JavaRush
*/
public class Solution {
    public static void main(String[] args) {
        //you can find your_file_name.tmp in your TMP directory or adjust outputStream/inputStream according to your file's actual location
        //вы можете найти your_file_name.tmp в папке TMP или исправьте outputStream/inputStream в соответствии с путем к вашему реальному файлу
        try {
            File yourFile = new File("C:\\Train on JAVA\\JavaRushTasks\\2.JavaCore\\src\\com\\javarush\\task\\task20\\task2002\\text.txt");
            OutputStream outputStream = new FileOutputStream(yourFile);
            InputStream inputStream = new FileInputStream(yourFile);

            JavaRush javaRush = new JavaRush();
            //initialize users field for the javaRush object here - инициализируйте поле users для объекта javaRush тут

            User user = new User();
            user.setFirstName("Bogdan");
            user.setLastName("Kupriyanov");
            user.setMale(true);
            user.setCountry(User.Country.UKRAINE);

            Date date = new Date(2001, Calendar.JANUARY,12);

            user.setBirthDate(date);

            javaRush.users.add(user);

            javaRush.save(outputStream);
            outputStream.flush();

            JavaRush loadedObject = new JavaRush();
            loadedObject.load(inputStream);
            //here check that the javaRush object is equal to the loadedObject object - проверьте тут, что javaRush и loadedObject равны
            System.out.println(javaRush.equals(loadedObject));

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Oops, something is wrong with my file");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Oops, something is wrong with the save/load method");
        }
    }

    public static class JavaRush {
        public List<User> users = new ArrayList<>();

        public void save(OutputStream outputStream) throws Exception {
            //implement this method - реализуйте этот метод
            PrintWriter printWriter = new PrintWriter(outputStream);

            printWriter.println(this.users.size());

            for (User user : users) {
                if (user != null) {
                    printWriter.println(user.getFirstName());
                    printWriter.println(user.getLastName());
                    printWriter.println(user.isMale());
                    printWriter.println(user.getCountry());
                    printWriter.println(user.getBirthDate().getTime());
                }
            }

            printWriter.flush();
            printWriter.close();
        }

        public void load(InputStream inputStream) throws Exception {
            //implement this method - реализуйте этот метод

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while (bufferedReader.ready()) {
                int size = Integer.parseInt(bufferedReader.readLine());

                if (size > 0) {

                    for (int i = 0; i < size; i++) {
                        User user = new User();

                        String str = bufferedReader.readLine();
                        user.setFirstName(str);

                        str = bufferedReader.readLine();
                        user.setLastName(str);

                        str = bufferedReader.readLine();
                        user.setMale(Boolean.parseBoolean(str));

                        str = bufferedReader.readLine();
                        user.setCountry(User.Country.valueOf(str));

                        str = bufferedReader.readLine();
                        long millis = Long.parseLong(str);
                        Date date = new Date(millis);

                        user.setBirthDate(date);

                        this.users.add(user);
                    }

                } else this.users = new ArrayList<>();
            }

            bufferedReader.close();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JavaRush javaRush = (JavaRush) o;

            return users != null ? users.equals(javaRush.users) : javaRush.users == null;

        }

        @Override
        public int hashCode() {
            return users != null ? users.hashCode() : 0;
        }
    }
}
