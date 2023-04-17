package octova.utils;

import java.util.ArrayList;

import org.bukkit.util.Vector;

public class Line {
	public static ArrayList<Vector> blocksInBetween(Vector a, Vector b) {
		ArrayList<Vector> line = new ArrayList<>();
		
		Vector p = new Vector(a.getX(), a.getY(), a.getZ());
		Vector step = new Vector(b.getX(), b.getY(), b.getZ());
		step.subtract(p);
		
		int iterations = 2 * (int)step.length();
		step.multiply(1.0 / (double)iterations);
		
		
		Vector past_block = new Vector(p.getBlockX(), p.getBlockY(), p.getBlockZ());
		
		for (int i = 0; i < iterations; ++i) {
			p.add(step);
			Vector block = new Vector(p.getBlockX(), p.getBlockY(), p.getBlockZ());
			if (!past_block.equals(block)) {
				line.add(block);
				past_block = block;
			}
		}
		
		if (past_block.equals(new Vector(b.getBlockX(), b.getBlockY(), b.getBlockZ()))
			&& !line.isEmpty()) {
			line.remove(line.size() - 1);
		}
		
		return line;
	}
}
