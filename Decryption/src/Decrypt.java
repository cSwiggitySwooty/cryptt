import java.math.BigInteger;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
public class Decrypt
{
	public static void main(String[] args) throws FileNotFoundException
	{
		if(args.length == 0)
		{
			System.out.println("No command line arguments were passed!");
			System.exit(0);
		}
		String cipherKey = args[0];
		String fileLocation = args[1];
		File secret = new File(fileLocation);
		Scanner ini = new Scanner(secret);
		Scanner s = new Scanner(secret);
		int lines = 0;
		while(ini.hasNextLine())
		{
			lines += 1;
			ini.nextLine();
//			text += s.nextLine();
		}
		String[] textArray = new String[lines];
		for(int i = 0; i < lines; i++)
		{
			textArray[i] = s.nextLine();
		}
		for(int i = 0; i < textArray.length; i++)
		{
			System.out.println(decrypt(textArray[i], passToNum((i % 20) + cipherKey)));
		}
		ini.close();
		s.close();
//		while(s.hasNextLine())
//		{
//			text += s.nextLine();
//		}
//		System.out.println(decrypt(text, passToNum(cipherKey)));
//		s.close();
	}
	public static BigInteger passToNum(String pass)
	{
		char letters[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char symbols[] = {' ', '!', '"', '#', '$', '%', '&', '(', ')', '*', '+', '\'', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}'};
		char numbers[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		char[] passArray = new char[pass.length()];
		String[] passNums = new String[pass.length()];
		int letterChanging = 0;
		String key = "";
		for(int i = 0; i < pass.length(); i++)
		{
			passArray[i] = pass.charAt(i);
		}
		for(int i = 0; i < pass.length(); i++)
		{
			for(int j = 0; j < letters.length; j++)
			{
				if(passArray[i] == letters[j])
				{
					letterChanging = j + 1;
					for(int z = i + 1; z > 0; z--)
					{
						letterChanging = letterChanging * z;
					}
					passNums[i] = String.valueOf(letterChanging);
					letterChanging = 0;
				}
			}
			for(int j = 0; j < symbols.length; j++)
			{
				if(passArray[i] == symbols[j])
				{
					passNums[i] = String.valueOf((int)(Math.sqrt(1 / Math.tan(j + 1))));
				}
			}
			for(int j = 0; j < numbers.length; j++)
			{
				if(passArray[i] == numbers[j])
				{
					passNums[i] = String.valueOf((int)Math.pow((((int)passArray[i] - 48)*(i + 1)), 2));
				}
			}
		}
		for(int i = 0; i < passNums.length; i++)
		{
			key += passNums[i];
		}
		BigInteger big = new BigInteger(key);
		for(int i = 0; i < pass.length(); i++)
		{
			big = big.multiply(big.add(big.sqrt()));
		}
		return big;
	}
	public static String decrypt(String cipherText, BigInteger key)
	{
		String plainText = "";
		char[] cipherTextArray = new char[cipherText.length()];
		char[] plainTextArray = new char[cipherText.length()];
		for(int i = 0; i < cipherText.length(); i++)
		{
			cipherTextArray[i] = cipherText.charAt(i);
		}
		String keyString = String.valueOf(key);
		while(cipherText.length() > keyString.length())
		{
			key = key.multiply(key.sqrt());
			keyString = String.valueOf(key);
		}
		for(int i = 0; i < cipherText.length(); i++)
		{
			plainTextArray[i] = (char)(cipherTextArray[i] - Character.getNumericValue(keyString.charAt(i)));
		}
		for(int i = 0; i < plainTextArray.length; i++)
		{
			plainText += plainTextArray[i];
		}
		return plainText;
	}
}
