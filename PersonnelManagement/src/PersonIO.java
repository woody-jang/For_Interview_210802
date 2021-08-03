import java.io.*;
import java.util.List;

public class PersonIO {
	static final File PEOPLE_LIST = new File("People.dat");

	private PersonIO() {
	}

	static void save(List<Person> employees) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PEOPLE_LIST))) {
			oos.writeObject(employees);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static List<Person> load() {
		List<Person> read = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PEOPLE_LIST))) {
			read = (List<Person>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return read;
	}
}
