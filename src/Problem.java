import java.util.ArrayList;
import java.util.List;

public class Problem 
{
	int total;
	List<Byte> coins;
	List<Combination> solutions;
	
	public Problem(List<Byte> coins, int total, int pbNumber)
	{
		this.coins = coins;
		this.total = total;
		System.out.println("Cas " + pbNumber + " :");
		solve();
	}
	
	private int countOccurances(List<Byte> array, int n)
	{
		int result = 0;
		for (int val : array)
			if (val == n)
				result++;
		return result;
	}
	
	public void printOptimalResult()
	{
		Combination result = solutions.get(0);
		System.out.println(result.array.size() + " pièces utilisées");
		for (int j = 0; j < coins.size(); j++)
			if (result.array.contains(coins.get(j)))
				System.out.print(countOccurances(result.array, coins.get(j)) + " fois " + coins.get(j) + ", ");
		System.out.println();
	}
	
	private void addToSolutions(Combination comb)
	{
		if (this.solutions == null)
		{
			this.solutions = new ArrayList<Combination>();
			this.solutions.add(comb);
			this.printOptimalResult();
		}
		else
			this.solutions.add(comb);
	}
	
	public void solve()
	{
		List<Combination> combTab = new ArrayList<Combination>();
		for (int i = 0; i < coins.size(); i++)
		{
			if (coins.get(i) > total)
			{
				this.coins = coins.subList(0, i);
				break;
			}
			else
			{
				Combination tempComb = new Combination(i);
				if(tempComb.sum == total)
					this.addToSolutions(tempComb);
				if(tempComb.sum + coins.get(tempComb.maxIndex) <= total)
					combTab.add(tempComb);
			}
		}
		for (int n = 1; n < (total / coins.get(0)); n++)
		{
			List<Combination> nextCombTab = new ArrayList<Combination>();
			for (Combination comb : combTab)
			{
				for (int i = comb.maxIndex; i < coins.size(); i++)
				{
					Combination tempComb = new Combination(comb);
					tempComb.add(i);
					if (tempComb.sum > total)
						break;
					else
					{
						if (tempComb.sum == total)
							this.addToSolutions(tempComb);
						if(tempComb.sum + coins.get(tempComb.maxIndex) <= total)
							nextCombTab.add(tempComb);
					}					
				}
			}
			combTab = nextCombTab;
		}
		if (this.solutions == null)
			System.out.println("Impossible de former la somme de " + total);
		else
			System.out.println(this.solutions.size() + " possibilités différentes de former la somme de " + total);
	}
	
	private class Combination 
	{
		int sum;
		int maxIndex;
		List<Byte> array;
				
		public Combination(int coinIndex)
		{
			this.array = new ArrayList<Byte>();
			byte coin = coins.get(coinIndex);
			this.array.add(coin);
			this.sum = coin;
			this.maxIndex = coinIndex;
		}
		
		public Combination(Combination comb)
		{
			this.sum = comb.sum;
			this.maxIndex = comb.maxIndex;
			this.array = new ArrayList<Byte>(comb.array);
		}
		
		public void add(int coinIndex)
		{
			byte coin = coins.get(coinIndex);
			this.array.add(coin);
			this.sum += coin;
			if (coinIndex > this.maxIndex)
				this.maxIndex = coinIndex;
			
		}
	}
}