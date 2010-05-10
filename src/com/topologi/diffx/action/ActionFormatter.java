package com.topologi.diffx.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.topologi.diffx.config.DiffXConfig;
import com.topologi.diffx.event.DiffXEvent;
import com.topologi.diffx.format.DiffXFormatter;

/* ============================================================================
 * ARTISTIC LICENCE
 * 
 * Preamble
 * 
 * The intent of this document is to state the conditions under which a Package
 * may be copied, such that the Copyright Holder maintains some semblance of 
 * artistic control over the development of the package, while giving the users
 * of the package the right to use and distribute the Package in a more-or-less
 * customary fashion, plus the right to make reasonable modifications.
 *
 * Definitions:
 *  - "Package" refers to the collection of files distributed by the Copyright 
 *    Holder, and derivatives of that collection of files created through 
 *    textual modification.
 *  - "Standard Version" refers to such a Package if it has not been modified, 
 *    or has been modified in accordance with the wishes of the Copyright 
 *    Holder.
 *  - "Copyright Holder" is whoever is named in the copyright or copyrights 
 *    for the package.
 *  - "You" is you, if you're thinking about copying or distributing this 
 *    Package.
 *  - "Reasonable copying fee" is whatever you can justify on the basis of 
 *    media cost, duplication charges, time of people involved, and so on. 
 *    (You will not be required to justify it to the Copyright Holder, but only 
 *    to the computing community at large as a market that must bear the fee.)
 *  - "Freely Available" means that no fee is charged for the item itself, 
 *    though there may be fees involved in handling the item. It also means 
 *    that recipients of the item may redistribute it under the same conditions
 *    they received it.
 *
 * 1. You may make and give away verbatim copies of the source form of the 
 *    Standard Version of this Package without restriction, provided that you 
 *    duplicate all of the original copyright notices and associated 
 *    disclaimers.
 *
 * 2. You may apply bug fixes, portability fixes and other modifications 
 *    derived from the Public Domain or from the Copyright Holder. A Package 
 *    modified in such a way shall still be considered the Standard Version.
 *
 * 3. You may otherwise modify your copy of this Package in any way, provided 
 *    that you insert a prominent notice in each changed file stating how and 
 *    when you changed that file, and provided that you do at least ONE of the 
 *    following:
 * 
 *    a) place your modifications in the Public Domain or otherwise make them 
 *       Freely Available, such as by posting said modifications to Usenet or 
 *       an equivalent medium, or placing the modifications on a major archive 
 *       site such as ftp.uu.net, or by allowing the Copyright Holder to 
 *       include your modifications in the Standard Version of the Package.
 * 
 *    b) use the modified Package only within your corporation or organization.
 *
 *    c) rename any non-standard executables so the names do not conflict with 
 *       standard executables, which must also be provided, and provide a 
 *       separate manual page for each non-standard executable that clearly 
 *       documents how it differs from the Standard Version.
 * 
 *    d) make other distribution arrangements with the Copyright Holder.
 *
 * 4. You may distribute the programs of this Package in object code or 
 *    executable form, provided that you do at least ONE of the following:
 * 
 *    a) distribute a Standard Version of the executables and library files, 
 *       together with instructions (in the manual page or equivalent) on where
 *       to get the Standard Version.
 *
 *    b) accompany the distribution with the machine-readable source of the 
 *       Package with your modifications.
 * 
 *    c) accompany any non-standard executables with their corresponding 
 *       Standard Version executables, giving the non-standard executables 
 *       non-standard names, and clearly documenting the differences in manual 
 *       pages (or equivalent), together with instructions on where to get 
 *       the Standard Version.
 *
 *    d) make other distribution arrangements with the Copyright Holder.
 *
 * 5. You may charge a reasonable copying fee for any distribution of this 
 *    Package. You may charge any fee you choose for support of this Package. 
 *    You may not charge a fee for this Package itself. However, you may 
 *    distribute this Package in aggregate with other (possibly commercial) 
 *    programs as part of a larger (possibly commercial) software distribution 
 *    provided that you do not advertise this Package as a product of your own.
 *
 * 6. The scripts and library files supplied as input to or produced as output 
 *    from the programs of this Package do not automatically fall under the 
 *    copyright of this Package, but belong to whomever generated them, and may
 *    be sold commercially, and may be aggregated with this Package.
 *
 * 7. C or perl subroutines supplied by you and linked into this Package shall 
 *    not be considered part of this Package.
 *
 * 8. The name of the Copyright Holder may not be used to endorse or promote 
 *    products derived from this software without specific prior written 
 *    permission.
 * 
 * 9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED 
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF 
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * ============================================================================
 */

/**
 * Generates a list of actions from the output of the algorithms.
 * 
 * @author Christophe Lauret
 * @version 11 December 2008
 */
public class ActionFormatter implements DiffXFormatter {

	/**
	 * The list of actions produced by this formatter.
	 */
	private final List<Action> actions = new ArrayList<Action>();

	/**
	 * The action used in the last operation.
	 */
	private Action action = null;

	/**
	 * Adds the event to an action of type 'DEL'.
	 * 
	 * @see Operator#DEL
	 * 
	 * @param e The event to delete.
	 */
	public void delete(DiffXEvent e) throws IOException, IllegalStateException {
		setupAction(Operator.DEL);
		this.action.add(e);
	}

	/**
	 * Adds the event to an action of type 'KEEP'.
	 * 
	 * @see Operator#KEEP
	 * 
	 * @param e The event to format.
	 */
	public void format(DiffXEvent e) throws IOException, IllegalStateException {
		setupAction(Operator.KEEP);
		this.action.add(e);
	}

	/**
	 * Adds the event to an action type 'INS'.
	 * 
	 * @see Operator#INS
	 * 
	 * @param e The event to insert.
	 */
	public void insert(DiffXEvent e) throws IOException, IllegalStateException {
		setupAction(Operator.INS);
		this.action.add(e);
	}

	/**
	 * Does nothing - the configuration does not affect the action.
	 */
	public void setConfig(DiffXConfig config) {
	}

	/**
	 * Returns the list of actions generated by this formatter.
	 * 
	 * @return the list of actions generated by this formatter.
	 */
	public List<Action> getActions() {
		return this.actions;
	}

	/**
	 * Sets up the action prior to handling the operation.
	 * 
	 * If the action does not exist or is of a different type, create a new one
	 * and add to the list of actions.
	 * 
	 * @param type The type of action.
	 */
	private void setupAction(Operator type) {
		if (action == null || action.type() != type) {
			this.action = new Action(type);
			this.actions.add(this.action);
		}
	}

}
