import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class SortedPlaylist {
    
    //Create a linked list, read the file names into it, and return the list
    public static LinkedList createFileList() {
        //Assume the list of file names is stored in a text file
        //Ask user for the name of this file
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Name of file containing list of files: ");
        String listName = keyboard.next();
        keyboard.close();
        LinkedList fileNames = new LinkedList();
        try {
            File fileList = new File(listName);
            Scanner scanner = new Scanner(fileList);
            while (scanner.hasNext()) {
                String fileName = scanner.next();
                fileNames.add(fileName);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File containing list of file names not found.");
            e.printStackTrace();
        }
        return fileNames;
    }
    
    //Read file into 1D array and return the array
    public static String[] readFile(File file, int length, int linesToIgnore) {
        /*Put each line as an element in a 1D array,
        excluding the first few lines as specified by linesToIgnore*/
        String[] a = new String[length];
        try {
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < linesToIgnore; i++) {
                scanner.nextLine();
            }
            int i = 0;
            while (scanner.hasNextLine() && i < length) {
                a[i] = scanner.nextLine();
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return a;
    }
    
    //set a song's value from a line of the csv file
    public static Song getSong(String line) {
        //Divide the line into an array of words
        String[] words = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        //Each word is an attribute that is added to an instance of the Song class
        String songTitle = words[1];
        //we don't want quotes in song titles
        songTitle = songTitle.replace("\"", "");
        String artistName = words[2];
        artistName = artistName.replace("\"", "");
        int averageStreamsCount = Integer.parseInt(words[3]);
        Song song = new Song(songTitle, averageStreamsCount, artistName);
        return song;
    }
    
    //Add song title to correct position in sorted linked list
    public static void addSong(LinkedList<Song> list, Song song) {
        //If the list is empty just add the title at the head of the list
        //This conditional is only true once
        if (list.isEmpty()) {
            list.add(song);
        //Insert the title in the right place
        } else if ( list.get(0).compareTo(song) > 0) {
            list.add(0, song);
        } else if ( list.get(list.size() - 1).compareTo(song) < 0) {
            list.add(list.size(), song);
        } else {
            int i = 0;
            while (list.get(i).compareTo(song) <= 0) {
                if (list.get(i).compareTo(song) == 0) {
                    return;
                }
                i++;
            }
            list.add(i, song);
        }
    }
    
    //Create a linked list implementation of a BST of every song in all the files
    public static LinkedList<Song> createPlaylist(LinkedList fileNames) {
        LinkedList<Song> playlist = new LinkedList<Song>();
        ListIterator<String> listIterator = fileNames.listIterator();
        while (listIterator.hasNext()) {
            File currentFile = new File(listIterator.next());
            String[] arr = readFile(currentFile, 200, 2);
            for (int i = 0; i < 200; i++) {
                Song song = getSong(arr[i]);
                addSong(playlist, song);
            }
        }
        return playlist;
    }
    
    //Create an empty file with its name as the argument
    public static File createFile(String name) {
        try{
            File file = new File(name);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            return file;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
    
    public static void writeToFile(LinkedList<Song> playlist, File file) {
        try {
            FileWriter writer = new FileWriter(file.getName());
            //for each artist name
            for (int i = 0; i < playlist.size(); i++) {
                Song currentSong = playlist.get(i);
                writer.write(currentSong.toString() + ", ");
                writer.write(currentSong.getAverageStreamsCount() + ", ");
                writer.write(currentSong.getArtistName());
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //write to file, but only from a certain range of the alphabetized list
    public static void writeSubset(LinkedList<Song> playlist, File file, Song start, Song end) {
        try {
            FileWriter writer = new FileWriter(file.getName());
            //for each artist name
            for (int i = playlist.indexOf(start); i <= playlist.indexOf(end); i++) {
                Song currentSong = playlist.get(i);
                writer.write(currentSong.toString() + ", ");
                writer.write(currentSong.getAverageStreamsCount() + ", ");
                writer.write(currentSong.getArtistName());
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        //Create a linked list of file names
        LinkedList<String> fileNames = createFileList();
        //Create one list of all songs in all the files
        LinkedList<Song> playlist = createPlaylist(fileNames);
        //Create an empty file called playlist.txt
        String fileNameWrite = "playlist.txt";
        File file = createFile(fileNameWrite);
        //Write the playlist to the file
        writeToFile(playlist, file);
    } 
}
