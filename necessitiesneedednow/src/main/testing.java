import java.util.HashMap;
import java.util.Scanner;

public class testing {

    public static void main(String[] args) {

        HashMap currentInventory = new HashMap();
        HashMap currentShoppingCart = new HashMap();
        User Jenny = new User("Jenny", "yenny@yahoo.com", "317 Island Lane", currentInventory, currentShoppingCart);

        Scanner input = new Scanner(System.in);
        String nameOfItem = "";
        String quantityOfItem;
        int counter = 0;
        while (!nameOfItem.equals("done")) {
            counter++;
            System.out.println("Input item " + counter + "'s name here please. Type the word 'done' if you are finished.");
            nameOfItem = input.nextLine();
            if (nameOfItem.equals("done")) {counter--;}
            else {
                System.out.println("Please input the amount of that item you want in digits.");
                while(!input.hasNextInt()) {
                    input.next();
                    input.nextLine();
                    System.out.println("Please enter the amount you want in digits.");
                }
                quantityOfItem = input.next();
                input.nextLine();
                currentShoppingCart.put(nameOfItem, quantityOfItem);
            }
        }
        System.out.println("Looks like you have " + counter + " items in your cart! " + Jenny.getCurrentShoppingCart());
        System.out.println();
        System.out.println("Now we're gonna try removing items (in various ways) from the cart.");
        System.out.println();
        String optionForItemRemoval = "";
        while (!optionForItemRemoval.equals("Z")) {
            System.out.println("Do you want to remove an entire item or just a certain amount of an item?");
            System.out.println("Please enter A for entire item removal ||||| Please enter B for removing a certain amount");
            System.out.println("Please enter Z if you want to exit.");
            optionForItemRemoval = input.nextLine();
            if (optionForItemRemoval.equals("Z")) { }
            else {
                String nameOfItemToRemove;
                String nameOfItemToPartiallyRemove;

                if (optionForItemRemoval.equals("A")) {
                    System.out.println("Please enter the name of the item you want to remove.");
                    nameOfItemToRemove = input.nextLine();
                    if (currentShoppingCart.containsKey(nameOfItemToRemove)) {
                        currentShoppingCart.remove(nameOfItemToRemove);
                    } else {
                        System.out.println("Looks like that item didn't exist in the cart. Did you mis-spell it, perhaps?");
                        System.out.println();
                    }
                    System.out.println(Jenny.getCurrentShoppingCart());
                    System.out.println();
                } else {
                    if (optionForItemRemoval.equals("B")) {
                        System.out.println("Please enter the name of the item you want to remove some of.");
                        nameOfItemToPartiallyRemove = input.nextLine();
                        if (currentShoppingCart.containsKey(nameOfItemToPartiallyRemove)) {
                            System.out.println("How much of this item do you want to remove? You can remove up to " + currentShoppingCart.get(nameOfItemToPartiallyRemove) + " units of this item.");
                            int quantityToRemove = Integer.parseInt(input.nextLine());
                            if (Integer.parseInt((String) currentShoppingCart.get(nameOfItemToPartiallyRemove)) - quantityToRemove >= 0) {
                                currentShoppingCart.replace(nameOfItemToPartiallyRemove, currentShoppingCart.get(nameOfItemToPartiallyRemove), Integer.parseInt((String) currentShoppingCart.get(nameOfItemToPartiallyRemove)) - quantityToRemove);
                                if (currentShoppingCart.get(nameOfItemToPartiallyRemove).equals(0)) {
                                    currentShoppingCart.remove(nameOfItemToPartiallyRemove);
                                }
                                System.out.println(Jenny.getCurrentShoppingCart());
                                System.out.println();
                            } else {
                                System.out.println("Uh oh. Looks like you tried to take more than you actually had in your cart...");
                                System.out.println(Jenny.getCurrentShoppingCart());
                                System.out.println();
                            }
                        } else {
                            System.out.println("Looks like we don't have that in your cart right now. Did you mis-spell it?");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Looks like you didn't put in A, B, or Z. Oops.");
                        System.out.println();
                    }
                }
            }
        }
    }
}
