package com.hellscape.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.hellscape.character.Entity;
import com.hellscape.character.Slime;
import com.hellscape.ui.Drawable;
import com.hellscape.ui.GamePanel;
import com.hellscape.util.Box;
import com.hellscape.util.Point;

public class Map implements Drawable {
    
    public final int maxTypeCol = 16;
    public final int maxTypeRow = 18;
    public final int maxWorldCol = 57;
    public final int maxWorldRow = 57;
    public final int roomSize = 5;
    public final int totalRoom = 7;
    public final int maxFloor = 3;

    private GamePanel gp;
    private BufferedImage[][] sprite;

    public Tile[][] world;
    public Point worldStart;
    public Point worldEnd;

    public List<Point> rooms;
    public List<Entity> enemies;
    public int floorCount = 1;
    public Door door;

    public Map(GamePanel gp) {
        this.gp = gp;
        this.sprite = new BufferedImage[maxTypeRow][maxTypeCol];
        this.world = new Tile[maxWorldRow][maxWorldCol];
        this.enemies = new ArrayList<Entity>();
        this.loadSprite("/tileset/heaven.png");
    }

    @Override
    public void update() {
        for (int i = 0; i < enemies.size(); i++) {
            Slime enemy = (Slime)enemies.get(i);
            if (enemy.isDead() == false) enemy.update();
            else {
                enemies.remove(enemy);
                i--;
            }
        }
        this.door.update();
    }

    @Override
    public void draw(Graphics2D g) {
        int minWorldCol = Math.max((gp.player.worldX-gp.screenWidth/2)/(gp.tileSize), 0);
        int maxWorldCol = Math.min((gp.player.worldX+gp.screenWidth/2)/(gp.tileSize)+1, this.maxWorldCol-1);
        int minWorldRow = Math.max((gp.player.worldY-gp.screenHeight/2)/(gp.tileSize), 0);
        int maxWorldRow = Math.min((gp.player.worldY+gp.screenHeight/2)/(gp.tileSize)+1, this.maxWorldRow);
        
        for (int col = minWorldCol; col <= maxWorldCol; col++)
        for (int row = minWorldRow; row <= maxWorldRow; row++) {
            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g.drawImage(this.world[row][col].sprite, screenX, screenY, null);
        }
    }

    public boolean isCollide(Box box) {
        boolean isCollide = false;
        int leftCol = (box.getX())/gp.tileSize;
        int rightCol = (box.getX()+box.getWidth())/gp.tileSize;
        int topRow = (box.getY())/gp.tileSize;
        int bottomRow = (box.getY()+box.getHeight())/gp.tileSize;
        
        isCollide |= !this.world[topRow][leftCol].isPassable;
        isCollide |= !this.world[topRow][rightCol].isPassable;
        isCollide |= !this.world[bottomRow][leftCol].isPassable;
        isCollide |= !this.world[bottomRow][rightCol].isPassable;

        isCollide |= this.door.isCollide(box);
        
        return isCollide;
    }

    public void generate() {
        RandomMap rm = new RandomMap(maxWorldCol, maxWorldRow);
        int[][] mapCode = rm.generate(roomSize, totalRoom);

        for (int col = 0; col < maxWorldRow; col++)
        for (int row = 0; row < maxWorldCol; row++) {
            int spriteCol = (mapCode[row][col] & 31) >> 1;
            int spriteRow = (mapCode[row][col] >> 5);
            if ((mapCode[row][col] & 1) == 0) spriteRow += 16;
            boolean isPassable = (mapCode[row][col] & 1) == 0;
            this.world[row][col] = new Tile(this.sprite[spriteRow][spriteCol], isPassable);
        }

        this.worldStart = rm.getStart();
        this.worldEnd = rm.getEnd();
        this.rooms = rm.getRooms();
        if (this.door == null) this.door = new Door(gp);
        else this.door.reset();
    }

    public void generateEnemies() {
        for (Point room : rooms) {
            room.scale(gp.tileSize, gp.tileSize);
            this.enemies.add(new Slime(gp, room.x, room.y));
            this.enemies.add(new Slime(gp, room.x + gp.tileSize, room.y));
            this.enemies.add(new Slime(gp, room.x - gp.tileSize, room.y));
            this.enemies.add(new Slime(gp, room.x, room.y + gp.tileSize));
            this.enemies.add(new Slime(gp, room.x, room.y - gp.tileSize));
        }
    }

    public void reset() {
        this.enemies.clear();
        this.floorCount = 1;
    }

    private void loadSprite(String path) {
        try {
            BufferedImage sprite = ImageIO.read(getClass().getResource(path));
            for (int col = 0; col < maxTypeCol; col++)
            for (int row = 0; row < maxTypeRow; row++) {
                this.sprite[row][col] = new BufferedImage(gp.tileSize, gp.tileSize, sprite.getType());
                Graphics2D g = this.sprite[row][col].createGraphics();
                
                g.drawImage(
                    sprite, 0, 0, gp.tileSize, gp.tileSize,
                    col * gp.spriteSize, row * gp.spriteSize,
                    (col+1) * gp.spriteSize, (row+1) * gp.spriteSize, null
                );
                
                g.dispose();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
