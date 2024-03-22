package FileIO;


import Constraints.Constraint;
import Constraints.Particle;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;


public class FileIO {

    public void save(ArrayList<Particle> particles, ArrayList<Constraint> constraints){
        System.out.println("this should save");
        try {
            BufferedWriter particleWriter = new BufferedWriter(new FileWriter("Week4/001.Verlet/resources/saveConstraints.txt"));

            for (Particle particle : particles) {
                particleWriter.write("\n" + particle);
            }

            particleWriter.close();

            BufferedWriter contraintWriter = new BufferedWriter(new FileWriter("Week4/001.Verlet/resources/saveConstraints.txt"));

            for (Constraint constraint : constraints) {
                contraintWriter.write("\n" + constraint);
            }

            contraintWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Particle> loadParticles(){
        System.out.println("this should load particles");
        ArrayList<Particle> particles = new ArrayList<>();


        return particles;
    }

    public ArrayList<Constraint> loadConstraints(){
        System.out.println("this should load constraints");
        ArrayList<Constraint> constraints = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Week4/001.Verlet/resources/saveConstraints.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return constraints;
    }
}
