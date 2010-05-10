package com.topologi.diffx.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.topologi.diffx.event.DiffXEvent;

/**
 * The action digester reads a list of actions and with an input sequence can
 * produce an output sequence.
 * 
 * The digester can be used to verify that a sequence of actions is a solution
 * to a DiffX problem.
 * 
 * @author Christophe Lauret
 * @version 11 December 2008
 */
public class ActionsUtils {

	/**
	 * Generates the list of events from the list of actions.
	 * 
	 * @param actions
	 *            The list of actions.
	 * @param positive
	 *            <code>true</code> for generating the new sequence;
	 *            <code>false</code> for generating the old sequence.
	 */
	public static List<DiffXEvent> generate(List<Action> actions, boolean positive) {
		List<DiffXEvent> generated = new LinkedList<DiffXEvent>();
		for (Action action : actions) {
			if (positive ? action.type() == Operator.INS : action.type() == Operator.DEL) {
				generated.addAll(action.events());
			} else if (action.type() == Operator.KEEP) {
				generated.addAll(action.events());
			}
		}
		return generated;
	}

	/**
	 * Returns the minimal string from the list of actions.
	 * 
	 * @param actions The list of actions.
	 */
	public static Operator[] minimal(List<Action> actions) {
		ArrayList<Operator> minimal = new ArrayList<Operator>();
		for (Action action : actions) {
			for (Operator op : action.minimal()) {
			  minimal.add(op);
			}
		}
		Operator[] ops = new Operator[minimal.size()];
		return minimal.toArray(ops);
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isValid(List<DiffXEvent> a, List<DiffXEvent> b, List<Action> actions) {
		int i = 0;
		int j = 0;
		for (Action action : actions) {
			if (action.type() == Operator.KEEP) {
				for (DiffXEvent e : action.events()) {
					if (!e.equals(a.get(i))) return false;
					if (!e.equals(b.get(j))) return false;
					i++;
					j++;
				}
			} else if (action.type() == Operator.INS) {
				for (DiffXEvent e : action.events()) {
					if (!e.equals(b.get(j))) return false;
					j++;
				}
			} else if (action.type() == Operator.DEL) {
				for (DiffXEvent e : action.events()) {
					if (!e.equals(a.get(i))) return false;
					i++;					
				}
			}
			
		}
		return true;
	}

}
