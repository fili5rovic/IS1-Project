package client;

import java.util.Scanner;
import util.MenuUtil;
import util.RestUtil;

public class Client {

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            MenuUtil.printMenu();

            System.out.print("Select number: ");
            int opCode = scanner.nextInt();

            switch (opCode) {
                case 1:
                    RestUtil.post("UserResource/Mesto/JSON");
                    break;
                case 2:
                    RestUtil.post("UserResource/Korisnik/JSON");
                    break;
                case 3:
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new email: ");
                    String email = scanner.nextLine();
                    RestUtil.put("UserResource/Korisnik/" + userId + "?email=" + email);
                    break;
                case 4:
                    System.out.print("Enter user ID: ");
                    int userNum = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter city ID: ");
                    int cityNum = scanner.nextInt();
                    RestUtil.put("UserResource/Korisnik/" + userNum + "?idMesto=" + cityNum);
                    break;
                case 5:
                    RestUtil.post("AudioResource/Kategorija/JSON");
                    break;
                case 6:
                    RestUtil.post("AudioResource/AudioSnimak/JSON");
                    break;
                case 7:
                    System.out.print("Enter audio recording ID: ");
                    int audioId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String audioName = scanner.nextLine();
                    RestUtil.put("AudioResource/AudioSnimak/" + audioId + "?naziv=" + audioName);
                    break;
                case 8:
                    System.out.print("Enter audio ID: ");
                    int aid = scanner.nextInt();
                    System.out.print("Enter category ID: ");
                    int kid = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.postNoBody("AudioResource/AudioKategorija/link/" + aid + "/" + kid);
                    break;
                case 9:
                    RestUtil.post("SubscriptionResource/Paket/new");
                    break;
                case 10:
                    System.out.print("Enter package ID: ");
                    int pkgId = scanner.nextInt();
                    System.out.print("Enter new monthly price: ");
                    int price = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.put("SubscriptionResource/Paket/" + pkgId + "?cena=" + price);
                    break;
                case 11: // watch out for duplicates
                    RestUtil.post("SubscriptionResource/Pretplata/new");
                    break;
                case 12: // watch out for duplicates
                    RestUtil.post("SubscriptionResource/Slusanje/new");
                    break;
                case 13:
                    System.out.print("Enter user ID: ");
                    int favUserId = scanner.nextInt();
                    System.out.print("Enter audio ID: ");
                    int favAudioId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.postNoBody("SubscriptionResource/Omiljeni/link/" + favUserId + "/" + favAudioId);
                    break;
                case 14:
                    RestUtil.post("SubscriptionResource/Ocena/new");
                    break;
                case 15:
                    System.out.print("Enter user ID: ");
                    int rateUserId = scanner.nextInt();
                    System.out.print("Enter audio ID: ");
                    int rateAudioId = scanner.nextInt();
                    System.out.print("Enter new rating: ");
                    int rating = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.put("SubscriptionResource/Ocena/" + rateUserId + "," + rateAudioId + "?ocena=" + rating);
                    break;
                case 16:
                    System.out.print("Enter user ID: ");
                    int delUserId = scanner.nextInt();
                    System.out.print("Enter audio ID: ");
                    int delAudioId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.delete("SubscriptionResource/Ocena/" + delUserId + "," + delAudioId);
                    break;
                case 17:
                    System.out.print("Enter audio recording ID: ");
                    int delAudioRecId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.delete("AudioResource/AudioSnimak/" + delAudioRecId);
                    break;
                case 18:
                    RestUtil.get("UserResource/Mesto");
                    break;
                case 19:
                    RestUtil.get("UserResource/Korisnik");
                    break;
                case 20:
                    RestUtil.get("AudioResource/Kategorija");
                    break;
                case 21:
                    RestUtil.get("AudioResource/AudioSnimak");
                    break;
                case 22:
                    System.out.print("Enter audio recording ID: ");
                    int getAudioId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.get("AudioResource/AudioSnimak/" + getAudioId + "/Kategorija");
                    break;
                case 23:
                    RestUtil.get("SubscriptionResource/Paket");
                    break;
                case 24:
                    System.out.print("Enter user ID: ");
                    int pretUserId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.get("SubscriptionResource/Pretplata/get/" + pretUserId);
                    break;
                case 25:
                    System.out.print("Enter audio ID: ");
                    int slusAudioId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.get("SubscriptionResource/Slusanje/get/" + slusAudioId);
                    break;
                case 26:
                    System.out.print("Enter audio ID: ");
                    int ocenaAudioId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.get("SubscriptionResource/Ocena/get/" + ocenaAudioId);
                    break;
                case 27:
                    System.out.print("Enter user ID: ");
                    int omiljeniUserId = scanner.nextInt();
                    scanner.nextLine();
                    RestUtil.get("SubscriptionResource/Omiljeni/get/" + omiljeniUserId);
                    break;
                case 0:
                    RestUtil.get("flush");
                    break;
                case -1:
                    return;
            }
        }
    }

}
