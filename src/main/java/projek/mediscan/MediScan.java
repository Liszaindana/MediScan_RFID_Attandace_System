/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package projek.mediscan;

import projek.object.Karyawan;
/**
 *
 * @author Syamsul Hidayatulloh
 */
public class MediScan {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Karyawan KR = new Karyawan();
        if(KR instanceof Karyawan){
            System.err.println("Karyawan");
        }else {
            System.err.println("Something else");
        }// //
    }
}
