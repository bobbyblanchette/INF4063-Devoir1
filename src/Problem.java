import java.util.ArrayList;
import java.util.List;

public class Problem 
{
	int total;
	List<Integer> coins;
	List<Combination> solutions;
	
	public Problem(List<Integer> coins, int total, int pbNumber)
	{
		this.coins = coins;
		this.total = total;
		System.out.println("Cas " + pbNumber + " :");
		solve();
	}
	
	private void printOptimalResult()
	{
		Combination result = solutions.get(0);
		System.out.println(result.count() + " pièces utilisées");
		for (int j = 0; j < coins.size(); j++)
		{
			if (result.coinFrequency[j] > 0)
				System.out.print(result.coinFrequency[j] + " fois " + coins.get(j) + ", ");
		}
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
	
	private void solve()
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
						{
							this.addToSolutions(tempComb);
						}
						if(tempComb.sum + coins.get(tempComb.maxIndex) <= total)
							nextCombTab.add(tempComb);
					}					
				}
			}
			combTab = nextCombTab;
		}
		if (this.solutions == null)
		{
			System.out.println("Impossible de former la somme de " + total);
		}
		else
		{
			System.out.println(this.solutions.size() + " possibilités différentes de former la somme de " + total);
		}	
	}
	
	private class Combination 
	{
		int sum;
		int maxIndex;
		int[] coinFrequency;
				
		public Combination(int coinIndex)
		{
			this.coinFrequency = new int[coins.size()];
			this.coinFrequency[coinIndex]++;
			this.sum = coins.get(coinIndex);
			this.maxIndex = coinIndex;
		}
		
		public Combination(Combination comb)
		{
			this.sum = comb.sum;
			this.maxIndex = comb.maxIndex;
			this.coinFrequency = comb.coinFrequency.clone();
		}
		
		public void add(int coinIndex){
			this.coinFrequency[coinIndex]++;
			this.sum += coins.get(coinIndex);
			if(coinIndex > this.maxIndex)
				this.maxIndex = coinIndex;
		}
		
		public int count(){
			int total = 0;
			for (int i : coinFrequency)
				total += i;
			return total;
		}
	}
}