//Paulina Przystupa
//16 August 2014

/*	This program allows you to sort citations a to z from  
   .txt file will and will export it as a .txt file with
	the now alphabatized sheet
 */
 
import java.io.*;
import java.util.*;
import java.awt.*;

public class CitationsAndAlphabetizerSAAStyle {
	
	public static void main (String[] args) throws FileNotFoundException{
		introduction();
      String type = "";
		String fileName = getsFileName();
		PrintStream citations = new PrintStream(new File(fileName));
		boolean cite = true;
		while (cite) {
			type = getType();
			if(type.toLowerCase().equals("book") ) {
				book(citations);
			} else if (type.toLowerCase().equals("edited book")) {
				editedBook(citations);
			} else if (type.toLowerCase().equals("journal article")) {
				journalArticle(citations);
			} else if (type.toLowerCase().equals("article in an edited volume")) {
				articleEditedVolume(citations);
			} else if (type.toLowerCase().equals("paper presented at a conference")) {
				presentedPaper(citations);
			} else if (type.toLowerCase().equals("dissertation or thesis")) {
				disserationOrThesis(citations);
			} else if (type.toLowerCase().equals("in press manuscript")) {
			 	inPressManuscript(citations);
	      } else if (type.toLowerCase().equals("unpublished manuscript")) {
				unpublishedManuscript(citations);
			} else if (type.toLowerCase().equals("contract report")) {
				contractReport(citations);
			} else if (type.toLowerCase().equals("translated book")) {
            translatedbook(citations);
         } else if (type.toLowerCase().equals("article in an edited volume from a series")) {
				articleEditedVolumeSeries(citations);
			} else {
				System.out.println("I cannot build a citation for that type of material");
			} 
			cite = questionYesNo("Would you like to write another citation?");
			System.out.println();
			System.out.println("Don't forget to italicize books and journals in final citation");
		}
		//All info needed
		int numberOfEntries = countsFileLines(fileName);
		//Makes necessary arrays
		String[] entry = new String[numberOfEntries];
		int[] rank = new int[numberOfEntries];
		String[] reordered = new String[numberOfEntries];
		//Meet of the problem
		entry = fillsArray(entry, fileName);
		rank = ranksEntries(numberOfEntries, entry); 
		reordered = getReorderedEntries(rank, entry, numberOfEntries);
		printsAlphabetizedCitations(fileName, reordered, numberOfEntries);
	}
	
	//Gets the name of the file to alphabetize
	public static String getsFileName() {
		System.out.println("What would you like to call this file? Please include the extension");
		Scanner console = new Scanner (System.in);
		return console.nextLine();
	}
	
	//Counts how many lines are in the file;
	public static int countsFileLines (String name)  throws FileNotFoundException{
		Scanner list = new Scanner (new File(name));
		int count = 0;
		while (list.hasNextLine()) {
			list.nextLine();
			count++;
		}
		return count;
	}
	
	//read through it and put each new line into the array
	public static String[] fillsArray (String[] s, String fileName) throws FileNotFoundException {
		int count = 0;
		Scanner citationsList = new Scanner (new File(fileName));
		while (citationsList.hasNextLine()) {
			s[count] = citationsList.nextLine();
			count++;
		}
		return s;
	}
	
	//Gets the rank for each entry
	public static int[] ranksEntries(int number, String [] s) {
		int count = 0;
		int [] rank = new int[number];
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		while (count < number) {
			int test = alphabet.indexOf(s[count].toLowerCase().charAt(0));
			int charCount = 0;
			for (int counter = 1; count + counter < number; counter++) {
				if (test > alphabet.indexOf(s[count + counter].toLowerCase().charAt(0))) {
					rank[count]++;
				} else if (test < alphabet.indexOf(s[count + counter].toLowerCase().charAt(0))) {
					rank[count + counter]++;				
				} else {
					while (alphabet.indexOf(s[count].toLowerCase().charAt(charCount)) == alphabet.indexOf(s[count + counter].toLowerCase().charAt(charCount))){
						charCount++;
					}
					if (alphabet.indexOf(s[count].toLowerCase().charAt(charCount)) > alphabet.indexOf(s[count + counter].toLowerCase().charAt(charCount))){
						rank[count]++;
					} else {
						rank[count + counter]++;
					}		
				}
			} 
			count++;
		}
		return rank;
	}
	
	//Get the new ordered array for the citations
	public static String[] getReorderedEntries (int [] rank, String [] entry, int number) {
		int count = 0;
		String [] reordered = new String [number];
		while (count < number) {
			reordered[rank[count]] = entry [count];	
			count++;
		}
		return reordered;
	}
	
	//Print out the citations in the correct order
	public static void printsAlphabetizedCitations(String fileName, String [] reordered, int number)throws FileNotFoundException {
		PrintStream citations = new PrintStream(new File("AlphabatizedCitations" + fileName));
		int count = 0;
		while (count < number) {
			Scanner line = new Scanner(reordered[count]);
			while(!line.hasNextInt()) {
				citations.print(line.next() + " ");
			}
			citations.println();
			citations.println(line.nextLine());
			citations.println();
			count++;
		}  
	}
	
	//Prints Introduction to program
   public static void introduction() {
		System.out.println("This program allows you to build citations for");
 		System.out.println("SAA Style or American Antiquity style citations");
		System.out.println("and export them as a .txt file to copy to whatever");
 		System.out.println("format you need. Please remember to italicize books");
      System.out.println("or journals as this is not included");
		System.out.println();
   } 
   
   //Gets the name the user wants to search for
   public static String getType() {
 		System.out.println();  
	   System.out.println("Is it a book, edited book, translated book, no author,"); 
		System.out.println("journal article, article in an edited volume, paper");
		System.out.println("presented at a conference, dissertation or thesis,contract report, ");
		System.out.println("unpublished manuscript, or an in press manuscript citation?");
      Scanner console = new Scanner(System.in);
      return console.nextLine();
   }
	 
	 public static void book(PrintStream citations) {
		citations.println(author() + "   " + year() + " " + title() + ". " + publisher() + ", " + city() + "." );
	 }
	 
	 public static void editedBook (PrintStream citations) {
		citations.println(editor() + " (editor)   " + year() + " " + title() + ". " + publisher() + ", " + city() + "." );
	 }
	 
	 public static void translatedbook(PrintStream citations) {
		citations.println(author() + "   " + year() + " " + title() + ". Translated by "
							+ info("Translator in format first last") + ". " + publisher() + ", " + city() + "." );
	 }
	
	public static void noAuthor(PrintStream citations) {
		citations.println(publisher() + "   " + year() + " " + title() + ". " + publisher() + ", " + city() + "." );
	}
	
	public static void journalArticle(PrintStream citations) {
		String issue = "";
		if (!questionYesNo("Does the journal do continuous pagination?")) {
			issue = info("Issue Number");
			issue =  "(" + issue + ")";
		}
		citations.println(author() + "   " + year() + " " + title() + ". " + journal() +
								" " + info("Volume") + issue +":"+ pageNumber() + "." );
	}
	
	public static void articleEditedVolume(PrintStream citations) {
		citations.println(author() + "   " + year() + " " + title() + ". In " + journal() 
								+ ", edited by " + editor() + ", pp." + pageNumber() + ". "
								+ publisher() + ", " + city() + ".");
	}
	
   public static void articleEditedVolumeSeries (PrintStream citations) {
		citations.println(author() + "   " + year() + " " + title() + ". In " + journal() 
								+ ", edited by " + editor() + ", pp. " + pageNumber() + ". "
								+ info("Name of Series") + ", Vol. " + info("Volume") + ", " + info("General Series Editor")
                        + ", general editor, " + info("Issuer of series") + ", " + city() + ".");
   }
   
	public static void presentedPaper(PrintStream citations) {
		citations.println(author() + "   " + year() + " " + title() + ". Paper presented at the " + 
								info("Name of the conference where it was presented including the year annual but not the city") +
								", " + city() + "." );
	}
	
	public static void disserationOrThesis(PrintStream citations) {
		String level = info("Was it a Ph.D dissertation or a Master's thesis (spell out which)?");
		if(questionYesNo("Did you consult a microform copy of the dissertation or thesis?")) {
			citations.println(author() + "   " + year() + " " + title() + ". " + level + ", " + info("Which University?") +
								", " + city() + ". University Microfilms, Ann Arbor." );
		} else {
			citations.println(author() + "   " + year() + " " + title() + ". Unpublished " + level + ", " + info("Which University?") +
									", " + city() + "." );
		}
	}
	
	public static void inPressManuscript(PrintStream citations) {
		citations.println(author() + "   " + year() + " " + title() + ". " + journal() + ", in press.");
	}
	
	public static void unpublishedManuscript(PrintStream citations) {
		citations.println(author() + "  " + year() + " " + title() + ". " + info("Is it a Manuscript or Drawings, type out which")
								+  " on file, " + info("Which Department holds this file?") + ", " + 
								info("Which organization is the department a part of?") + ", " + city());
	}
	
	public static void contractReport(PrintStream citations) {
		citations.println(author() + "  " + year() + " " + title() + ". " + info("Who produced this file?") + ". " + 
								info("Which organization was this submited to?") + ". Contract No. " + info("what is the contract number?")
								+ ". " + info("Where can you obtain copies of this report?") + ", " + city());
	}
	
   public static void name (PrintStream citations) {
      citations.println(author() + "   " + year() + " " + title () + ". ");
   }
   
	public static String title(){
		return info("Name of the work (the article, chapter, or book)");
	}
	
	public static String year(){
		return info("What year was it published?");
	}
	
	public static String author(){
		return info("Author in format last, first (for multiple last, first, first last, and first last): ");
	}
	
	public static String journal(){
		return info("What is the title of the journal or book the work was found in?");
	}
	 
	public static String city(){
		return info("In which city was this work published, presented, or written in?");
	}

	public static String publisher(){
		return info("Who is the publisher of the work?");
	}
	
	public static String editor(){
		return info("Compiler or Editor in first last (and first last) format");
	}

	public static String pageNumber() {
		return info("Page Numbers (#-#) format)");
	}
	
	public static String info(String neededInformation) {
	 	System.out.println(neededInformation);
		Scanner console = new Scanner(System.in);
		String answer = "";
		answer = console.nextLine();
		while(answer.equals("")){
		System.out.println("Sorry you didn't type anything");
		System.out.println();
		System.out.println(neededInformation);
		answer = console.nextLine();
		}
		return answer;
	 }
	
	public static boolean questionYesNo(String question) {
	 	System.out.println(question);
		Scanner console = new Scanner(System.in);
		return console.nextLine().toLowerCase().contains("y");		
	}
}