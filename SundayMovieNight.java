
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import static java.lang.System.*;
import java.util.*;

class SundayMovieNight extends JFrame {
	
	/**
	 * this is a class entirely containing the program for sunday movie nights
	 */
	private static final long serialVersionUID = 7717219514758657643L;
	private File hat = new File("hat.txt");
	private JTextField _movieInputTF = new JTextField(20);
   	private JTextField _movieOutputTF   = new JTextField(20);

	public static void main(String[] args) {
		SundayMovieNight window = new SundayMovieNight();

		window.setVisible(true);
		window.setSize(800, 110);
	}
	
	// constructor
	public SundayMovieNight() {
		
		// create components
		JButton addMovieBtn = new JButton("Add a Movie");
		JButton getMovieBtn = new JButton("Choose a Movie!");
		addMovieBtn.addActionListener(new addMovieBtnListener());
		getMovieBtn.addActionListener(new getMovieBtnListener());
		
		// create content pane & set layout
		JPanel content = new JPanel();
		content.setLayout(new FlowLayout());
		
		// add components to the content pane
        	content.add(new JLabel("Write down the exact title of a movie then press the button!"));
        	content.add(_movieInputTF);
        	content.add(addMovieBtn);
        	content.add(new JLabel("Press button to pull a movie out of the hat.txt!"));
        	content.add(getMovieBtn);
        	content.add(_movieOutputTF);
        
        	// set the windows attributes
        	setContentPane(content);
        	pack();
		
		setTitle("Sunday Movie Night Selector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	// center window
		
	}
	
	class addMovieBtnListener implements ActionListener {
		
        	public void actionPerformed(ActionEvent e) {
            
            		// Append Movie title to text file
            		try {
            			FileWriter fw = new FileWriter(hat, true);
            			BufferedWriter bw = new BufferedWriter(fw);
            			bw.write(_movieInputTF.getText() + '\n');
            			_movieInputTF.setText("");
            			bw.close();
            			fw.close();
            		} catch (IOException o) {
            			out.print("ERROR");
            		}
        	}
    	}
	
	class getMovieBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			HashMap<Integer, String> hmap = new HashMap<Integer, String>();
			Random random = new Random();
			int i = 0; // index used in hashmap

		    	// Read in all movie titles saved in text file
			try {
				FileReader fr = new FileReader(hat);
				BufferedReader br = new BufferedReader(fr);

				String str;
				while ((str = br.readLine()) != null) {
					hmap.put(i, str);
					i++;
				}

				br.close();
			} catch (IOException o) {
				out.println("ERROR");
			}

			// Randomly select one movie based on index
			if(i > 0) { // only do this if there is a movie in the file
				int randomInt = random.nextInt(i);
				String selMovie = hmap.get(randomInt);
				_movieOutputTF.setText("" + selMovie);

				// Delete all other instances of movie title by recreating string without selected movie copies
				String updatedStr = "";
				for(int k = i - 1; k >= 0; k--) {
					if(hmap.get(k) != selMovie) {
						updatedStr += hmap.get(k) + "\n";
					}
				}

				try {
					FileWriter fw = new FileWriter(hat);
					fw.write(updatedStr);
					fw.close();
				} catch (IOException o) {
					out.print("ERROR");
				}
			}
			else {
				_movieOutputTF.setText("Add some Movies!!!");
			}
		}
	}
}
