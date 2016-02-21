package com.mycompany.knight;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.ViewDebug.*;

public class MainActivity extends Activity 
{ 	static int[] stateA63 	= new int[64];
	int[] kstateA63 	= new int[66];
    int[] next8A512 = new int[512];
	int disp8A512count = 0;
	static int iterCount=0;
	static int cursor 		= 0;
	int[] cursor8A 	= new int[8];
	int cursorX 	=0;
	int cursorY 	=0;
	public static final int MAX64 		= 64;
	public static final int KNIGHT 		= 65;
	public static final int NONE 		= 66;
	public static final int OB 			= 67; //out of boundary
	public static final int NH 			= 68; // next hop good
	
	public static final int NoInt = 69;// used in trace as no value
	public static final String NoString = "NS";// used in trace as no string
	public static final String ResetTrace = "RT";// used in trace as no string
	
	
	String BLANK1 = "  ";
	String BLANK2 = " ";
	String mode = "DIAG";
	
	//TextView traceView = (TextView)findViewById(R.id.trace);

	public  void setTrace(String traceString,int traceInt)
	{
		TextView traceView = (TextView)findViewById(R.id.trace);
		
		if(traceString == ResetTrace){traceView.setText("");}
		if(traceString != NoString){
			traceView.setText(traceView.getText()+" "+traceString);
		}
		if(traceInt != NoInt){
			traceView.setText(traceView.getText()+" "+ Integer.toString(traceInt));
		}
		
	}

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		//TextView cursView = (TextView)findViewById(R.id.CursView);
		
		initialize();
		cursor =0;
		setCurYcurX(cursor);
		displayBoard();
		displayCursor();
		
		
    }
	
	

	private  void hopFrom(int spot)
	{
		
		int nextSpot = 0;
		
		iterCount++;
		setTrace(toYXstr(spot), NoInt);
		//setTrace("",stateA63[spot]);
		
		if (iterCount >= 200){return ;}
		if (stateA63[spot] != NONE){return ;}
		
		nextSpot = getNextHop(spot);
		//setTrace("NS ",nextSpot);
	
		if (nextSpot >= MAX64){return;}
		
		stateA63[spot] = nextSpot;
		cursor = nextSpot;
		hopFrom(nextSpot);
	}

	private String toYXstr(int spot)
	{
		// TODO: Implement this method
		String YXstr;
		YXstr= Integer.toString(spot/8)+Integer.toString(spot%8);
		return YXstr;
	}

	private  int getNextHop(int spot)
	{	//1.Get first best 8 hops
		//2.return hop if it is not marked
		//3.if hop marked try next hop
		
	
		int nextHop =MAX64;
		int inextHop;
		int i = 0;
		
		while(nextHop >= MAX64){
			// if(i==8){return MAX64;}
			if(i==8){break;} //return MAX64
			
			inextHop = next8A512[(spot *8) +i];
			
			if (inextHop < MAX64){
				
				if(stateA63[inextHop] == NONE){
					nextHop = inextHop;
					} 
				}
			
			i++;
		}
		
		return nextHop;
	}
	
	private  void toast(int num)
	{
	
		toast(Integer.toString(num));
		
	}
	private  void toast(String message)
	{
		Toast.makeText(MainActivity.this, 
					   message , Toast.LENGTH_LONG).show();
	}
	public void buttonClicked(View v) {

		//toast("Button Clicked");

		switch (v.getId()) {
			case R.id.upButton:{
					upButton();
					break;
				}
			case R.id.downButton:{
					
					downButton();
					break;
				}
			case R.id.leftButton:{
					leftButton();
					break;
				}
			case R.id.rightButton:{
					rightButton();
					break;
				}
			case R.id.startButton:{
					//rightButton();
					startButton();
					break;
				}
			case R.id.modeButton:{
					//rightButton();
					modeButton();
					break;
				}
			case R.id.dispButton:{
					
					Button dispButton = (Button)findViewById(R.id.dispButton);
					
					dispButton.setText("DISP: "+ Integer.toString(disp8A512count));
					display512A8();
					disp8A512count++;
					if(disp8A512count == 8){disp8A512count=0;}
					
					
					break;
				}

		}
	}

	private void modeButton()
	{
		// TODO: Implement this method
		Button modeButton = (Button)findViewById(R.id.modeButton);
		
		Button upButton = (Button)findViewById(R.id.upButton);
		Button downButton = (Button)findViewById(R.id.downButton);
		
		
		//modeButton.setTextColor(555);
		if (mode == "DIAG"){
			mode = "RUN";
			modeButton.setText("RUN");
			
			//upButton.setVisibility(View.INVISIBLE);
			//downButton.setVisibility(View.INVISIBLE);
			
			
		}else{
			mode = "DIAG";
			modeButton.setText("DIAG");
			
			upButton.setVisibility(View.VISIBLE);
			downButton.setVisibility(View.VISIBLE);
			
		}
		displayBoard();
	}

	private void startButton()
	{
		// TODO: Implement this method
		hopFrom(cursor);
		displayBoard();
		displayCursor();
	}

	private void rightButton()
	{
		if(mode == "DIAG"){
			
		if (cursorX < 7){
			cursorX = cursorX +1;
			cursor = cursor +1;
		}}
		
		if(mode == "RUN"){
			//TODO
			if (cursorX < 7){
				cursorX = cursorX +1;
				cursor = cursor +1;
			}
		
		}
		displayCursor();
		
	}

	private void leftButton()
	{	
		
		if(mode == "DIAG"){
		if (cursorX != 0){
			cursorX = cursorX -1;
			cursor = cursor -1;
		}}
		
		if(mode == "RUN"){
			
			//TODO
			if (cursorX != 0){
				cursorX = cursorX -1;
				cursor = cursor -1;
			}
			
		}
		displayCursor();
	}

	private void downButton()
	{
		if (cursorY < 7){
			cursorY = cursorY +1;
			cursor = cursor +8;
		}
		
		
		displayCursor();
	}

	private void upButton()
	{
		
		if (cursorY != 0){
			cursorY = cursorY -1;
			cursor = cursor - 8;
		}
		displayCursor();
	}
	private void initialize()
	{
		// initialize state data
		for (int st = 0; st <= 63; st++)
		{
			stateA63[st] = NONE;
		}
		
		initCursor8A();
		setNext8();
		initNext8A512();
		initKstate();
		
		
	}

	private void initNext8A512()
	{
		// TODO: Implement this method
		for (int n8 = 0; n8 <= 63; n8++)
		{
			cursor = n8;
			setCurYcurX(n8);
			
			setNext8();
			
			
			for(int i=0; i<= 7; i++){
				next8A512[(n8*8)+i] = cursor8A[i];
				//next8A512[(n8*8)+i] = i;
				}
		}
	}

	private void setCurYcurX(int tcursor)
	{
		// TODO: Implement this method
		cursorY = tcursor/8;
		cursorX = tcursor%8;
	}

	private void initCursor8A()
	{
		for (int n8 = 0; n8 == 7; n8++)
		{
			cursor8A[n8] = 0;
		}
	}

	private void setNext8()
	{
		
		
		int y12, y34,y56,y78;
		int x17,x28,x35,x46;
		
		y12 = cursorY - 2;
		y34 = cursorY - 1;
		y56 = cursorY + 1;
		y78 = cursorY + 2;
		
		x17 = cursorX - 1;
		x28 = cursorX + 1;
		x35 = cursorX - 2;
		x46 = cursorX + 2;
		
		initKstate();
		//1,2
		if (y12 < 0){
			cursor8A[0] = OB;
			cursor8A[1] = OB;
		
			}else{ 
				if ((x17 >= 0)&&(x17 <= 6))
				{cursor8A[0] = (y12*8)+x17;
				}else{cursor8A[0] = OB;}
				
				if ((x28 >= 1)&&(x28 <= 7))
				{cursor8A[1] = (y12*8)+x28;
				}else{cursor8A[1] = OB;}
			
		}
		//3,4
		if (y34 < 0){
			cursor8A[2] = OB;
			cursor8A[3] = OB;
			}else{ 
				//left X
				if ((x35 >= 0)&&(x35 <= 5)){
				cursor8A[2] = (y34*8)+x35;
				}else{cursor8A[2] = OB;}
				//right X
				if ((x46 >= 2)&&(x46 <= 7)){
				cursor8A[3] = (y34*8)+x46;
				}else{cursor8A[3] = OB;}
		}
			
		//5,6	
		
		if (y56 > 7){
			cursor8A[4] = OB;
			cursor8A[5] = OB;

			}else{ 
			//left X
			if ((x35 >= 0)&&(x35 <= 5)){
				cursor8A[4] = (y56*8)+x35;
			}else{cursor8A[4] = OB;}
			//right X
			if ((x46 >= 2)&&(x46 <= 7)){
				cursor8A[5] = (y56*8)+x46;
			}else{cursor8A[5] = OB;}
		}
		//7,8
		if (y78 >7){
			cursor8A[6] = OB;
			cursor8A[7] = OB;
			}else{ 
			//left X
			if ((x17 >= 0)&&(x17 <= 6)){
				cursor8A[6] = (y78*8)+x17;
			}else{cursor8A[6] = OB;}
			//right X
			if ((x28 >= 1)&&(x28 <= 7)){
				cursor8A[7] = (y78*8)+x28;
			}else{cursor8A[7] = OB;}
		}
		
		for(int spot : cursor8A){
			
			if (spot <= 64 ){
				//kstateA63[spot] = NH ;
				kstateA63[spot] = spot ;
			}
		}
	}

	private void toastDiag()
	{
		// TODO: Implement this method
		toast("Hello");
	}

	private void displayBoard()
	{	int stateSpot;
		String spotStr ;
		TextView state = (TextView)findViewById(R.id.mainTextView1);
		//state.setText("X 1 2 3 4 5 6 7 8 \n");
		String display = "X    0  1  2  3  4  5  6  7 \n";
		display = display + "Y|-------------------------\n";
		
		for (int y= 0; y < 8; y++)
		{
			display = display  + Integer.toString(y) + "| ";
			for(int x =0;x<8; x++){
				
				if(((y*8)+x) != cursor)
					{
						if (mode == "DIAG"){
							stateSpot = kstateA63[(y*8)+x];
							
						}else{
							stateSpot = stateA63[(y*8)+x];
							
						}
						
						
				}else{
					stateSpot= KNIGHT;
					}
				
				
				switch ( stateSpot ){
					
					case KNIGHT :{
							//spotStr = " X";
							spotStr = "\uD83D\uDC0E";
							
							break;}
					case NONE :{
							spotStr = "  ";
							break;}
					case  NH :{
							spotStr = " O";
							break;}
					default:{
							//spotStr = Integer.toString(stateSpot);
							spotStr = toYXstr(stateSpot);
							
					}
				} //switch
					
				
				display = display  + " " + spotStr ;
			}
			display = display  + "\n";
			
			state.setText(display);
			
		}
	}
		
	

	private void displayCursor()
	{
		TextView cursView = (TextView)findViewById(R.id.CursView);
		TextView cursXYView = (TextView)findViewById(R.id.CursXYView);
		
		TextView next8View = (TextView)findViewById(R.id.Next8View);
		TextView next8XYView = (TextView)findViewById(R.id.Next8XYView);
		
		cursView.setText(integerToDisplay(cursor));
		cursXYView.setText(Integer.toString(cursorY) + Integer.toString(cursorX));
		
		if(mode=="DIAG"){
		next8View.setText(getNext8Text());
		next8XYView.setText(getNext8XYText());
		}
		
		if(mode=="RUN"){
			next8View.setText(getNext8A512Text());
			next8XYView.setText(getNext8A512XYText());
		}
		displayBoard();
	}

	private CharSequence getNext8A512XYText()
	{
		// TODO: Implement this method
		int Y,X;
		
		//setTrace("",cursor);


		String next8A512XYText = "ST ";
		for (int ix = 0; ix <= 7;ix++){

			Y = (next8A512[cursor + ix]/8);
			X = (next8A512[cursor + ix]%8 );
			next8A512XYText = next8A512XYText + Integer.toString(Y) +Integer.toString(X) + " ";

		}

		
		return next8A512XYText+ " "+"NT";
	}

	private CharSequence getNext8A512Text()
	{
		
		String next8A512Text = "ST ";
		
		for (int ix = 0; ix <= 7;ix++){
			next8A512Text = next8A512Text + integerToDisplay(next8A512[cursor + ix]) + " ";

		}
		
		return next8A512Text+" "+"NT";
	}
	
	
	

	private CharSequence getNext8XYText()
	{   
		int Y,X;
		
		
		String next8XYtext = "";
		for (int ix = 0; ix <= 7;ix++){
			
			Y = (cursor8A[ix]/8);
			X = cursor8A[ix]%8 ;
			next8XYtext = next8XYtext + Integer.toString(Y) +Integer.toString(X) + " ";

		}

		//next8text = next8text + "hello2";
		return next8XYtext;
		
	}

	private CharSequence getNext8Text()
	{
		initCursor8A();
		String next8text = "";
		setNext8();
		for (int ix = 0; ix <= 7;ix++){
			next8text = next8text + integerToDisplay(cursor8A[ix]) + " ";
			
		}
		
		//next8text = next8text + "hello2";
		return next8text;
		
	}

	private CharSequence integerToDisplay(int cursor)
	{
		String twoChar;
		if(cursor <= 9){
			twoChar = "0" + Integer.toString(cursor);
		}else{
			twoChar = Integer.toString(cursor);
		}
		return twoChar;
	}
	
	private void initKstate()
	{
		// initialize state data
		for (int st = 0; st <= 63; st++)
		{
			kstateA63[st] = NONE;
		}

	}
	
	private void display512A8()
	{	int stateSpot;
		String spotStr ;
		TextView state = (TextView)findViewById(R.id.mainTextView1);
		//state.setText("X 1 2 3 4 5 6 7 8 \n");
		String display = "X    0  1  2  3  4  5  6  7 \n";
		display = display + "Y|-------------------------\n";

		for (int y= 0; y < 8; y++)
		{
			display = display  + Integer.toString(y) + "| ";
			for(int x =0;x<8; x++){

				if(((y*8)+x) != cursor)
				{
					
				//todo
				stateSpot = next8A512[(((y*8)+x)*8)+ disp8A512count];
				
				//stateSpot = (y*8)+x+ disp8A512count;
				}else{
					stateSpot= KNIGHT;
				}

				switch ( stateSpot ){

					case KNIGHT :{
							//spotStr = " X";
							spotStr = "\uD83D\uDC0E";

							break;}
					case NONE :{
							spotStr = "  ";
							break;}
					case  NH :{
							spotStr = " O";
							break;}
					default:{
							//spotStr = Integer.toString(stateSpot);
							spotStr = Integer.toString(stateSpot/8)+Integer.toString(stateSpot%8);

						}
				} //switch


				display = display  + " " + spotStr ;
			}
			display = display  + "\n";

			state.setText(display);

		}
	}
}
