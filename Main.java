import java.util.Scanner;
class Main {
    enum State {
        UNFINISHED,
        DRAW,
        XWIN,
        OWIN,
        IMPOSSIBLE
    }
    public static int countWins(char[][] arr, char c) {
        int winCount = 0;
        for (int i = 0; i < 3; i++) {
            if (arr[i][0] == c && arr[i][1] == c && arr[i][2] == c) {
                winCount++;
            }
            if (arr[0][i] == c && arr[1][i] == c && arr[2][i] == c) {
                winCount++;
            }
        }
        if (arr[0][0] == c && arr[1][1] == c && arr[2][2] == c) {
            winCount++;
        }
        if (arr[2][0] == c && arr[1][1] == c && arr[0][2] == c) {
            winCount++;
        }
        return winCount;
    }

    public static int countChars(char[][] arr, char c) {
        int outNum = 0;
        for (char[] sArr : arr) {
            for (char xoSpace : sArr) {
                outNum = xoSpace == c ? outNum + 1 : outNum;
            }
        }
        return outNum;
    }

    public static State checkState(char[][] arr) {
        if (countWins(arr, 'X') + countWins(arr, 'O') > 1) {
            return State.IMPOSSIBLE;
        } else if (Math.abs(countChars(arr, 'X') - countChars(arr, 'O')) > 1) {
            return State.IMPOSSIBLE;
        } else if (countWins(arr, 'X') == 1) {
            return State.XWIN;
        } else if (countWins(arr, 'O') == 1) {
            return State.OWIN;
        } else if (countChars(arr, ' ') == 0){
            return State.DRAW;
        } else {
            return State.UNFINISHED;
        }
    }

    public static void printGame(char[][] arr) {
        System.out.println("---------");
        System.out.println("| " + arr[0][0] + " " + arr[0][1] + " " + arr[0][2] + " |");
        System.out.println("| " + arr[1][0] + " " + arr[1][1] + " " + arr[1][2] + " |");
        System.out.println("| " + arr[2][0] + " " + arr[2][1] + " " + arr[2][2] + " |");
        System.out.println("---------");
    }

    public static void printState(char[][] arr) {
        switch (checkState(arr)) {
            case IMPOSSIBLE:
                System.out.println("Impossible");
                break;
            case XWIN:
                System.out.println("X wins");
                break;
            case OWIN:
                System.out.println("O wins");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
            case UNFINISHED:
                System.out.println("Game not finished");
            default:
        }
    }

    public static boolean checkInput(char[][] arr, String input) {
        boolean outBool;
        try {
            int x = Integer.parseInt("" + input.charAt(0));
            int y = Integer.parseInt("" + input.charAt(2));
        } catch (Exception e) {
            System.out.println("You should enter numbers!");
            return false;
        }
        int x = Integer.parseInt("" + input.charAt(0));
        int y = Integer.parseInt("" + input.charAt(2));

        if (x > 3 || x < 1 || y > 3 || y < 1) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        switch (arr[3 - y][x - 1]) {
            case 'X':
            case 'O':
                System.out.println("This cell is occupied! Choose another one!");
                outBool = false;
                break;
            case ' ':
                outBool = true;
                break;
            default:
                outBool = false;
                System.out.println("default hit L113");
                break;
        }
        return outBool;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //System.out.print("Enter cells: ");
        //String input = scanner.nextLine();
        String input = "_________";

        char[][] gameArray = new char[3][3];
        for (int i = 0, j = 0, k = 0; i < 9; i++) {
            if (input.charAt(i) == '_') {
                gameArray[j][k++] = ' ';
            } else {
                gameArray[j][k++] = input.charAt(i);
            }

            if (k == 3) {
                j++;
                k = 0;
            }
        }
        printGame(gameArray);
        //printState(gameArray);
        int x;
        int y;
        String input2;
        boolean validInput;
        char currentPlayer = 'X';
        while (checkState(gameArray) == State.UNFINISHED) {
            do {
                System.out.print("Enter the coordinates: ");
                input2 = scanner.nextLine();
                validInput = checkInput(gameArray, input2);
            } while (!validInput);
            x = Integer.parseInt("" + input2.charAt(0));
            y = Integer.parseInt("" + input2.charAt(2));
            gameArray[3 - y][x - 1] = currentPlayer;
            printGame(gameArray);
            currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
        }
        printState(gameArray);
    }
}