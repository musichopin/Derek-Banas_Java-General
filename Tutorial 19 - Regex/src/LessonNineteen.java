import java.util.regex.*;

public class LessonNineteen{
	
	public static void main(String[] args){
		
		String longString = " Derek Banas CA 12345 PA (412)555-1212 johnsmith@hotmail.com 412-555-1234 412 555-1234 1-(412)555-1212"; 
		String strangeString = " 1Z aaa **** *** {{{ {{ { ";
		
//		***LONG STRING***
		System.out.println("***Long String***");

		
		/*
		[ ]  Insert characters that are valid
//		[A-Za-z ]
		[^ ]  Insert characters (or space) that are not valid:
//		[^T-Z^f-g^ ]
		\\s  Any white space (aranan kelimeden 1 önce ve 1 sonra var ise): 
//		starting index aradýðýmýz kelimedeki ilk karakterden önceki boþluðun bulunduðu yer iken 
//		ending index, kelime sonrasý boþluðun bulunduðu yerin 1 fazlasýdýr. outputta gösterilmez.
		\\S  Any non white space (aranan kelimeden 1 önce ve 1 sonra var ise): 
//		starting index aranan kelimedeki karakterden önceki karakterin bulunduðu yer iken ending index, 
//		aranan kelimedeki son karakterin bulunduðu yerin 1 fazlasýdýr. outputta gösterilir. 
		{n,m}  Whatever proceeds must occur between n and m times:
//		m 2 ise aranan kelime 2 karakterlidir, 
// 	baþlangýçta ve sonda bir tane \\S var ise fazladan 2 karakter olur, 
//   	\\s var ise de ayný durum geçerlidir ama trim() kullanýlýrsa boþluk gözükmediði için anlaþýlmaz

		kýsýtlayý sýrasý olarak, \\s, [] ve {n,m} dikkate alýnmalýdýr
		regex hata verdiðinde (karakter türü, sayýsý vs) hatalý karakter atlanýr ve regex querye devam eder
		*/
		
		// Word must contain letters that are 2 to 20 characters in length
		// [A-Za-z]{2,20} 0r \\w{2,20}
		
		regexChecker("\\s[A-Za-z]{2,20}\\s", longString);
//		"\\s\\s[A-Za-z]{2,20}\\s" denseydi öncesinde min 2 tane space gerekecekti
		
		/*
		\\d  Any digits 0-9
	 	\\D  Anything not a number
	 	{n}  Whatever proceeds must occur n times
	 	*/
		
		// Only 5 digits
		// [0-9]{5}\\s or \\d{5}
		
		regexChecker("\\s\\d{5}\\s", longString);
//		alt:
//		regexChecker("(\\s\\d{5}\\s)", longString);
		
		/*
		|  Is used for OR clause situations
		*/
		
		// Must start with a A or C, followed by 1 letter in brackets
		// Must be a maximum of 2 characters in length
		// A[KLRZ]|C[AOT]
		
		regexChecker("A[KLRZ]|C[AOT]", longString);
		
		
		
		
//		***STRANGE STRING***
		System.out.println("***Strange String***\n");

		/*
		{n,}  Whatever proceeds must occur at least n times
		+  Whatever proceeds must occur one or more times
		. ^ * + ? { } [ ] \ | ( )  Characters that must be escaped or backslashed
		*/
		
		// Grab any string that contains 1 or more "{"!
//		paranthesis breaks the results out so you can group them (optional in this example). 
//		You use it when you want to save a bunch of results into an array for example
		regexChecker("(\\{{1,})", strangeString);
		regexChecker("(\\{+)", strangeString);
		
		// Get anything that occurs 3 times except newline
		// .  Anything but newline
		
		regexChecker(".{3}", strangeString);
		
		/*
		\\w  Any word type character A-Z, a-z, 0-9, _
		\\W  Any non word character
		*  Occurs zero or more times
		*/
		
		regexChecker("\\w*", strangeString);
		
		
		
//		***LONG STRING***
		System.out.println("***Long String***\n");
		
		
//		plus sign (+) means 1 or more of any of these different groups joined by the + sign
//		look for email adress:
		regexChecker("[A-Za-z0-9._%\\-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", longString);
		
		
		// ? 0 or 1 of what proceeds it
//		? is used if we don't know necessarily know if sth is gonna exist (used for phone numbers)
//		first question mark pertains to ( |-) on its left side (sol tarafýna olmasýna dikkat)
//		ne zaman parantez kullanýlmalý: | iþaretinde, ?'in sol tarafýnda
		
//		\\(?[0-9]{3}\\)?( |-)? yazmak daha mantýklý
		regexChecker("([0-9]( |-)?)?(\\(?[0-9]{3}\\)?|[0-9]{3})( |-)?(([0-9]{3}( |-)?[0-9]{4})|[a-zA-Z0-9]{7})", longString);
//		1-(412)555-1212  (412)555-1212  412-555-1234  412 555-1234
//		([0-9]( |-)?)? yukarýdaki nolardan "1-" ý niteler
//		(\\(?[0-9]{3}\\)?|[0-9]{3})( |-)? "(412)", "412-" ve "412 " yi niteler (veya ' \\(?[0-9]{3}\\)?( |-)? ')
//		[0-9]{3}( |-)? "555-" i niteler
//		[0-9]{4} "1212" yi niteler
//		[a-zA-Z0-9]{7} "555-1212" yi niteler (insanlatýn letter code a göre no yazma durumuna karþýn)
		
		
		regexReplace(longString);
		
	}
	
	public static void regexChecker(String theRegex, String str2Check){
		
		// You define your regular expression (REGEX) using Pattern
		
		Pattern checkRegex = Pattern.compile(theRegex);
		
		// Creates a Matcher object that searches the String for
		// anything that matches the REGEX
		
		Matcher regexMatcher = checkRegex.matcher( str2Check );
		
		// Cycle through the positive matches and print them to screen
		// Make sure string isn't empty and trim off any whitespace
		
		while ( regexMatcher.find() ){
			if (regexMatcher.group().length() != 0){ // for security
				System.out.println( regexMatcher.group().trim() );
//				group() gets the sole regex value 
//				if trim is used we omit the whitespace
//				(eþleþtikten sonra trimler)
				
				// You can get the starting and ending indexes
				
				System.out.println( "Start Index: " + regexMatcher.start());
				System.out.println( "End Index: " + regexMatcher.end());
			}
		}
		
		System.out.println();
	}
	
	public static void regexReplace(String str2Replace){
//		this method will replace the space with comma and space
		
		// REGEX that matches 1 or more white space
		
		Pattern replace = Pattern.compile("\\s+");
//		we defined our regular expression with \\s+
		
		// This is how you ignore case
		// Pattern replace = Pattern.compile("[A-Z]+", Pattern.CASE_INSENSITIVE);
		//	same as typing [A-Za-z]+
		
		// trim the string t prepare it for a replace
//		trims both ends of the string (eþleþmeden önce trimler)
		Matcher regexMatcher = replace.matcher(str2Replace.trim());
		// Creates a Matcher object that searches the String for
		// anything that matches the REGEX
		
		// replaceAll replaces all white space with commas
		
		System.out.println(regexMatcher.replaceAll(", "));
		
	}
	
}
/*
***Long String***

Derek
Start Index: 0
End Index: 7
CA
Start Index: 12
End Index: 16
PA
Start Index: 21
End Index: 25

12345
Start Index: 15
End Index: 22

CA
Start Index: 13
End Index: 15

***Strange String***

{{{
Start Index: 17
End Index: 20
{{
Start Index: 21
End Index: 23
{
Start Index: 24
End Index: 25

{{{
Start Index: 17
End Index: 20
{{
Start Index: 21
End Index: 23
{
Start Index: 24
End Index: 25

1Z
Start Index: 0
End Index: 3
aa
Start Index: 3
End Index: 6
a *
Start Index: 6
End Index: 9
***
Start Index: 9
End Index: 12
**
Start Index: 12
End Index: 15
* {
Start Index: 15
End Index: 18
{{
Start Index: 18
End Index: 21
{{
Start Index: 21
End Index: 24

1Z
Start Index: 1
End Index: 3
aaa
Start Index: 4
End Index: 7

***Long String***

johnsmith@hotmail.com
Start Index: 39
End Index: 60

(412)555-1212
Start Index: 25
End Index: 38
412-555-1234
Start Index: 61
End Index: 73
412 555-1234
Start Index: 74
End Index: 86
1-(412)555-1212
Start Index: 87
End Index: 102

Derek, Banas, CA, 12345, PA, (412)555-1212, johnsmith@hotmail.com, 412-555-1234, 412, 555-1234, 1-(412)555-1212
*/