import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.abs;

public class Main {

    static int canvasWidth = 400;
    static int canvasHeight = 600;

    static void startScreen(int fps, double speed, int interceptorX, int interceptorY) {

        StdDraw.picture(canvasWidth / 2, canvasHeight / 2, "assets/background.png", 400, 600);
        StdDraw.picture(200, 400, "assets/title.png", 300, 100);

        StdDraw.setPenColor(StdDraw.SILVER);
        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 20));
        StdDraw.text(200, 300, "> Start Game <");
        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 10));
        StdDraw.text(200, 280, "Fps: ~" + fps + " | Speed: " + speed);
        StdDraw.setFont(new Font("Helvetica", Font.PLAIN, 10));
        StdDraw.text(200, 80, "Move: [←] [↑] [→] [↓]");
        StdDraw.text(200, 65, "Shoot: [Space]");
        StdDraw.text(200, 50, "Press [ENTER] to start");
        StdDraw.text(200, 35, "FPS: A/D Speed: Q/E");

        StdDraw.picture(interceptorX, interceptorY, "assets/interceptor.png", 40, 50);

    }

    public static void main(String[] args) {

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0.0, canvasWidth);
        StdDraw.setYscale(0.0, canvasHeight);
        StdDraw.setTitle("2042: Interceptor");
        StdDraw.enableDoubleBuffering();
        Random random = new Random();
        boolean start_screen = true;
        boolean exit = false;


        while(!exit){
            boolean game_screen = true;
            boolean menu_screen = false;
            boolean canShoot =true;

            int interceptorX = 200;
            int interceptorY = 200;
            int fps = 20;
            double speed = 20.0;
            int enemySpeed = 5;
            int bulletSpeed = 8;
            int score = 0;
            int hearts = 3;
            int m = 0;

            ArrayList<Integer> enemyX = new ArrayList<>();
            enemyX.add(50);
            enemyX.add(150);
            enemyX.add(250);
            enemyX.add(350);
            enemyX.add(50);
            enemyX.add(150);
            enemyX.add(250);
            enemyX.add(350);

            ArrayList<Integer> enemyY = new ArrayList<>();
            enemyY.add(550);
            enemyY.add(550);
            enemyY.add(550);
            enemyY.add(550);
            enemyY.add(490);
            enemyY.add(490);
            enemyY.add(490);
            enemyY.add(490);

            ArrayList<Integer> bulletX = new ArrayList<>();
            ArrayList<Integer> bulletY = new ArrayList<>();
            ArrayList<Integer> enemyBulletX = new ArrayList<>();
            ArrayList<Integer> enemyBulletY = new ArrayList<>();
            ArrayList<Integer> explosionX = new ArrayList<>();
            ArrayList<Integer> explosionY = new ArrayList<>();
            ArrayList<Integer> explosionTimer = new ArrayList<>();
            ArrayList<Integer> heartsX = new ArrayList<>();
            ArrayList<Integer> heartsY = new ArrayList<>();

            startScreen(fps, speed, interceptorX, interceptorY);

            while (start_screen) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_A) && fps < 100) {
                    fps += 5;
                    StdDraw.pause(100);
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_D) && fps - 5 > 1) {
                    fps -= 5;
                    StdDraw.pause(100);
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_Q) && speed < 40) {
                    speed++;
                    StdDraw.pause(100);
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_E) && speed > 0) {
                    speed--;
                    StdDraw.pause(100);
                }

                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && interceptorX > 20) interceptorX = interceptorX - (int) speed;
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && interceptorX < 380)
                    interceptorX = interceptorX + (int) speed;
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && interceptorY < 575) interceptorY = interceptorY + (int) speed;
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && interceptorY > 25) interceptorY = interceptorY - (int) speed;

                if (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) canShoot = true;
                if (canShoot && StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                    bulletX.add(interceptorX);
                    bulletY.add(interceptorY);
                    canShoot = false;
                }

                StdDraw.clear();
                startScreen(fps, speed, interceptorX, interceptorY);
                for (int i = 0; i < bulletY.size(); i++) {
                    bulletY.set(i, bulletY.get(i) + bulletSpeed);

                    if (bulletY.get(i) > canvasHeight) {
                        bulletY.remove(i);
                        bulletX.remove(i);
                        i--;
                        continue;
                    }
                    StdDraw.picture(bulletX.get(i), bulletY.get(i), "assets/bullet.png", 4, 10);

                }

                StdDraw.show();
                StdDraw.pause(1000 / fps);

                if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                    interceptorX = 200;
                    interceptorY = 200;
                    bulletX.clear(); ;
                    bulletY.clear();
                    start_screen = false;
                }

            }

            boolean paused = false;
            boolean flagP = false;
            while (game_screen) {
                StdDraw.clear();
                StdDraw.picture(canvasWidth / 2, canvasHeight / 2, "assets/background.png", 400, 600);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 15));
                StdDraw.textLeft(10, 585, "Score: " + score);

                int heartX0 = 385;
                for (int i = 0; i < hearts; i++) {
                    StdDraw.picture(heartX0, 585, "assets/heart.png", 20, 20);
                    heartX0 -= 20;
                }

                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && interceptorX > 20)
                    interceptorX = interceptorX - (int) speed;
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && interceptorX < 380)
                    interceptorX = interceptorX + (int) speed;
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && interceptorY < 575)
                    interceptorY = interceptorY + (int) speed;
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && interceptorY > 25)
                    interceptorY = interceptorY - (int) speed;
                if (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) canShoot = true;
                if (canShoot && StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                    bulletX.add(interceptorX);
                    bulletY.add(interceptorY);
                    canShoot = false;
                }// shoot

                boolean changeDirection = false;
                for (int i = 0; i < enemyX.size(); i++) {
                    int enemyNextX = enemyX.get(i) + enemySpeed;
                    if (enemyNextX > canvasWidth - 25 || enemyNextX < 25) {
                        changeDirection = true;
                        break;
                    }
                }   // checks if there is any enemyship touching frame
                if (changeDirection) enemySpeed *= -1;
                for (int i = 0; i < enemyX.size(); i++) {
                    enemyX.set(i, enemyX.get(i) + enemySpeed);
                    StdDraw.picture(enemyX.get(i), enemyY.get(i), "assets/enemyFighter.png", 50, 50);
                }   // draws enemy ships

                for (int i = 0; i < explosionX.size(); i++) {
                    if (explosionTimer.get(i) < 11) {
                        StdDraw.picture(explosionX.get(i) - 3, explosionY.get(i) + 3, "assets/explosionSmall.png", 30, 30);
                    } else if (explosionTimer.get(i) < 21) {
                        StdDraw.picture(explosionX.get(i) + 3, explosionY.get(i) - 3, "assets/explosionBig.png", 45, 45);
                    } else {
                        explosionX.remove(i);
                        explosionY.remove(i);
                        explosionTimer.remove(i);
                        i--;
                    }

                }   //draws explosions

                for (int i = 0; i < enemyX.size(); i++) {
                    if (enemyX.size() > 3) {
                        if (random.nextDouble() < 0.04) {
                            enemyBulletX.add(enemyX.get(i));
                            enemyBulletY.add(enemyY.get(i));
                        }
                    } else {
                        if (random.nextDouble() < 0.1) {
                            enemyBulletX.add(enemyX.get(i));
                            enemyBulletY.add(enemyY.get(i));
                        }
                    }
                }   // fires bullets randomly based on the number of enemy ships

                for (int i = 0; i < enemyBulletX.size(); i++) {
                    enemyBulletY.set(i, enemyBulletY.get(i) - bulletSpeed);
                    if (enemyBulletY.get(i) < 0) {
                        enemyBulletX.remove(i);
                        enemyBulletY.remove(i);
                        i--;
                        continue;
                    }

                    if (abs(interceptorX - enemyBulletX.get(i)) <= 22 && abs(interceptorY - enemyBulletY.get(i)) <= 30) {
                        enemyBulletX.remove(i);
                        enemyBulletY.remove(i);
                        i--;
                        if (hearts>0) hearts--;
                        continue;
                    }
                    StdDraw.picture(enemyBulletX.get(i), enemyBulletY.get(i), "assets/enemyBullet.png", 4, 10);
                } // draws enemy bullets and checks if any enemy bullet touches interceptor

                for (int i = 0; i < heartsX.size(); i++) {
                    heartsY.set(i, heartsY.get(i) - bulletSpeed);
                    if (heartsY.get(i) < 0) {
                        heartsX.remove(i);
                        heartsY.remove(i);
                        i--;
                        continue;
                    }

                    if (abs(interceptorX - heartsX.get(i)) <= 30 && abs(interceptorY - heartsY.get(i)) <= 35) {
                        heartsX.remove(i);
                        heartsY.remove(i);
                        i--;
                        if (hearts < 5) hearts++;
                        continue;
                    }
                    StdDraw.picture(heartsX.get(i), heartsY.get(i), "assets/heart.png", 20, 20);
                } // draws hearts and checks if any heart touches interceptor

                StdDraw.picture(interceptorX, interceptorY, "assets/interceptor.png", 40, 50);
                for (int i = 0; i < bulletY.size(); i++) {
                    bulletY.set(i, bulletY.get(i) + bulletSpeed);

                    if (bulletY.get(i) > canvasHeight) {
                        bulletY.remove(i);
                        bulletX.remove(i);
                        i--;
                        continue;
                    }

                    boolean collision = false;
                    for (int j = 0; j < enemyX.size(); j++) {
                        if (abs(enemyX.get(j) - bulletX.get(i)) <= 22 && abs(enemyY.get(j) - bulletY.get(i)) <= 30) {
                            bulletX.remove(i);
                            bulletY.remove(i);
                            i--;
                            explosionX.add(enemyX.get(j));
                            explosionY.add(enemyY.get(j));
                            if (random.nextInt(3) == 0) {
                                heartsX.add(enemyX.get(j));
                                heartsY.add(enemyY.get(j));
                            }
                            enemyX.remove(j);
                            enemyY.remove(j);
                            score += 30;
                            collision = true;
                            break;
                        }
                    }
                    if (collision) {
                        explosionTimer.add(0);
                        continue;
                    }
                    StdDraw.picture(bulletX.get(i), bulletY.get(i), "assets/bullet.png", 4, 10);
                } // move every bullet and check if there is collision between bullet and enemy

                for (int i = 0; i < explosionTimer.size(); i++) {
                    explosionTimer.set(i, explosionTimer.get(i) + 1);
                }

                for (int i = 0; i < enemyX.size(); i++) {
                    if (abs(interceptorX - enemyX.get(i)) <= 45 && abs(interceptorY - enemyY.get(i)) <= 50) {
                        enemyX.remove(i);
                        enemyY.remove(i);
                        score += 30;
                        i--;
                        if (hearts>0) hearts--;

                    }
                } //checks if enemy ship and interceptor collide

                if (enemyX.isEmpty() || hearts == 0) {
                    menu_screen = true;
                    game_screen = false;
                }

                if (!StdDraw.isKeyPressed(KeyEvent.VK_P)) {
                    flagP = true;
                }
                if (flagP && StdDraw.isKeyPressed(KeyEvent.VK_P)) {
                    paused = true;
                    flagP = false;
                }


                while (paused) {
                    StdDraw.clear();
                    StdDraw.picture(canvasWidth / 2, canvasHeight / 2, "assets/background.png", 400, 600);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font("Helvetica", Font.BOLD, 15));
                    StdDraw.textLeft(10, 585, "Score: " + score);

                    heartX0 = 385;
                    for (int i = 0; i < hearts; i++) {
                        StdDraw.picture(heartX0, 585, "assets/heart.png", 20, 20);
                        heartX0 -= 20;
                    }

                    for (int i = 0; i < enemyX.size(); i++) {
                        StdDraw.picture(enemyX.get(i), enemyY.get(i), "assets/enemyFighter.png", 50, 50);
                    }   // draws enemy ships

                    for (int i = 0; i < explosionX.size(); i++) {
                        if (explosionTimer.get(i) < 11) {
                            StdDraw.picture(explosionX.get(i) - 3, explosionY.get(i) + 3, "assets/explosionSmall.png", 30, 30);
                        } else if (explosionTimer.get(i) < 21) {
                            StdDraw.picture(explosionX.get(i) + 3, explosionY.get(i) - 3, "assets/explosionBig.png", 45, 45);
                        }
                    } // explosions

                    for (int i = 0; i < enemyBulletX.size(); i++) {
                        StdDraw.picture(enemyBulletX.get(i), enemyBulletY.get(i), "assets/enemyBullet.png", 4, 10);
                    } // draws enemybullets

                    for (int i = 0; i < heartsX.size(); i++) {
                        StdDraw.picture(heartsX.get(i), heartsY.get(i), "assets/heart.png", 20, 20);
                    } // draws hearts moving

                    StdDraw.picture(interceptorX, interceptorY, "assets/interceptor.png", 40, 50);

                    for (int i = 0; i < bulletY.size(); i++) {
                        StdDraw.picture(bulletX.get(i), bulletY.get(i), "assets/bullet.png", 4, 10);
                    } // draw every bullet

                    StdDraw.picture(canvasWidth / 2, canvasHeight / 2, "assets/paused.png", 200, 50);
                    StdDraw.show();
                    StdDraw.pause(100);

                    if (!StdDraw.isKeyPressed(KeyEvent.VK_P)) {
                        flagP = true;
                    }

                    if (flagP && StdDraw.isKeyPressed(KeyEvent.VK_P)) {
                        flagP = false;
                        paused = false;
                    }

                }

                StdDraw.show();
                StdDraw.pause(1000 / fps);
            }

            while (menu_screen) {
                StdDraw.clear();
                StdDraw.picture(canvasWidth / 2, canvasHeight / 2, "assets/background.png", 400, 600);
                if (enemyX.isEmpty()) StdDraw.picture(canvasWidth / 2, 390, "assets/victory.png", 250, 60);
                else if (hearts == 0) StdDraw.picture(canvasWidth / 2, 405, "assets/gameOver.png", 250, 110);

                if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) m = 0;
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) m = 1;
                if (StdDraw.isKeyPressed(KeyEvent.VK_ENTER)) {
                    if (m == 0) break;

                    else {
                        exit=true;
                        break;
                    }
                }

                if (m == 0) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
                    StdDraw.text(canvasWidth / 2, 340, "Score: " + score);
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 22));
                    StdDraw.text(canvasWidth / 2, 260, "> Restart <");
                    StdDraw.setPenColor(StdDraw.SILVER);
                    StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
                    StdDraw.text(canvasWidth / 2, 230, " End Game ");
                }
                else {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
                    StdDraw.text(canvasWidth / 2, 340, "Score: " + score);
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 22));
                    StdDraw.text(canvasWidth / 2, 230, "> End Game < ");
                    StdDraw.setPenColor(StdDraw.SILVER);
                    StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
                    StdDraw.text(canvasWidth / 2, 260, " Restart ");
                }

                StdDraw.show();
                StdDraw.pause(100);

            }
        }

        System.exit(0);

    }
}
