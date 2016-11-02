import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Devoir1 
{
	public static void main(String[] args) 
	{
		if (args.length == 1)
		{
			readFile(args[0]);
		}
		else
		{
			System.out.println("Argument invalide.");
		}
		
	}
	
	public static void readFile(String path)
	{
		try (BufferedReader br = new BufferedReader(new FileReader(path)))
		{
			int nbOfProblems = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < nbOfProblems; i++)
			{
				br.skip(2);
				String[] coinsStr = br.readLine().split("\\s+");
				List<Integer> coins = Arrays.stream(coinsStr).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
				int total = Integer.parseInt(br.readLine());
				new Problem(coins, total, i+1);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NumberFormatException e)
		{
			System.out.println("The input is in a wrong format");
			e.printStackTrace();
		}
	}
}


