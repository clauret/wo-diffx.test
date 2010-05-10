package com.topologi.diffx.action;

/**
 * The different basic types of difference operators used by DiffX.
 * 
 * 
 * @author Christophe Lauret
 * @version 12 December 2008
 */
public enum Operator {
	INS  { public String toString() {return "+";}},
	DEL  { public String toString() {return "-";}},
	KEEP { public String toString() {return "=";}};
}
