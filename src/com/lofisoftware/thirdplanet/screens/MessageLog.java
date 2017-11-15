package com.lofisoftware.thirdplanet.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Cell;
import com.lofisoftware.thirdplanet.Assets;
import com.lofisoftware.thirdplanet.messages.Message;

public class MessageLog extends Table {

	public final int MAX_MESSAGES = 20;
	private ScrollPane scroller;
	private List<Message> messages = new ArrayList<Message>();
	private LabelStyle labelStyle;
	
	public MessageLog() {
		super();
		this.setSkin(Assets.getUiskin());
		labelStyle = new LabelStyle(Assets.getSmallFont(), Color.WHITE);
		
		this.align(Align.left);
		//this.debugCell();
		
		scroller = new ScrollPane(this);
		//scroller.setBounds(this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight());
		scroller.setScrollingDisabled(true, false);
		
		//scroller.setScrollBarPositions(false, true);
		//scroller.setForceScroll(false, true);
	}

	public ScrollPane getScroller() {
		return scroller;
	}

	public void record(Message message){
		messages.add(message);
	}
	
    public void updateMessages() {
        
        for (Message m : messages){
        	Label label = new Label(m.text(), labelStyle);
        	label.setWrap(true);
        	label.setWidth(getWidth());
        	this.add(label).align(Align.left).width(getWidth());
        	scroller.setScrollPercentY(1);
        	if (this.getCells().size() > MAX_MESSAGES)
        		removeTopCell();
		}
		messages.clear();
    }
    
    public void removeTopCell(){
    	List<Cell> cells = this.getCells(); 

        //Remove contents of first row
        cells.get(0).setWidget(null);

        //Copy all cells up one row
        for (int i=0;i<cells.size()-1;i++){
            cells.set(i, cells.get(i+1));
        }

        //Remove the last row
        cells.remove(cells.size()-1);
    }
}
