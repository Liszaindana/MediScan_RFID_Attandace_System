/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package projek.mediscan;

import projek.mediscan;

public class MainApp {
    public static void main(String[] args) {
        Karyawan KR = new Karyawan();
        if(KR instanceof Karyawan){
            System.err.println("Karyawan");
        }else {
            System.err.println("Something else");
        }// //
        // //
    }
}