import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.56.2:4567/madang", "root", "1234");
            Statement stmt = con.createStatement();

            while (true) {
                System.out.println("\n=== MySQL Database Program ===");
                System.out.println("1. 데이터 검색");
                System.out.println("2. 데이터 삽입");
                System.out.println("3. 데이터 삭제");
                System.out.println("4. 종료");
                System.out.print("선택하세요: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.println("\n--- 데이터 검색 ---");
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
                    System.out.println("ID | 제목 | 출판사 | 가격");
                    while (rs.next()) {
                        System.out.println(
                                rs.getInt("bookid") + " | " +
                                        rs.getString("bookname") + " | " +
                                        rs.getString("publisher") + " | " +
                                        rs.getInt("price")
                        );
                    }
                    rs.close();
                }
                else if (choice == 2) {
                    System.out.println("\n--- 데이터 삽입 ---");
                    System.out.print("도서 ID 입력: ");
                    int bookid = scanner.nextInt();
                    scanner.nextLine(); // 버퍼 비우기
                    System.out.print("도서 제목 입력: ");
                    String bookname = scanner.nextLine();
                    System.out.print("출판사 입력: ");
                    String publisher = scanner.nextLine();
                    System.out.print("가격 입력: ");
                    int price = scanner.nextInt();

                    String insertQuery = "INSERT INTO Book (bookid, bookname, publisher, price) VALUES (" +
                            bookid + ", '" + bookname + "', '" + publisher + "', " + price + ")";
                    stmt.executeUpdate(insertQuery);
                    System.out.println("데이터가 성공적으로 삽입되었습니다.");
                }
                else if (choice == 3) {
                    System.out.println("\n--- 데이터 삭제 ---");
                    System.out.print("삭제할 도서 ID 입력: ");
                    int bookid = scanner.nextInt();

                    String deleteQuery = "DELETE FROM Book WHERE bookid = " + bookid;
                    int rowsAffected = stmt.executeUpdate(deleteQuery);
                    if (rowsAffected > 0) {
                        System.out.println("데이터가 성공적으로 삭제되었습니다.");
                    } else {
                        System.out.println("삭제할 데이터가 존재하지 않습니다.");
                    }
                }
                else if (choice == 4) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }
                else {
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
                }
            }
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
                System.out.println("데이터베이스 연결이 종료되었습니다.");
            } catch (Exception e) {
                System.out.println("연결 종료 중 오류 발생: " + e.getMessage());
            }
            scanner.close();
        }
    }
}
