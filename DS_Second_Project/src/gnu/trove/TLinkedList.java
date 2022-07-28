///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001, Eric D. Friedman All Rights Reserved.
// Copyright (c) 2009, Rob Eden All Rights Reserved.
// Copyright (c) 2009, Jeff Randall All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////


package gnu.trove;

import gnu.trove.impl.TLinkable;
import java.io.*;
import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;



/**
 * <p>A LinkedList implementation which holds instances of type
 * <tt>TLinkable</tt>.
 * <p/>
 * Using this implementation allows you to get java.util.LinkedList
 * behavior (a doubly linked list, with Iterators that support insert
 * and delete operations) without incurring the overhead of creating
 * <tt>Node</tt> wrapper objects for every element in your list.
 * <p/>
 * The requirement to achieve this time/space gain is that the
 * Objects stored in the List implement the <tt>TLinkable</tt>
 * interface.
 * <p/>
 * The limitations are: <ul>
 * <li>the same object cannot be put into more than one list at the same time.
 * <li>the same object cannot be put into the same list more than once at the same time.
 * <li>objects must only be removed from list they are in.  That is,
 * if you have an object A and lists l1 and l2, you must ensure that
 * you invoke List.remove(A) on the correct list.
 * <li> It is also forbidden to invoke List.remove() with an unaffiliated
 * TLinkable (one that belongs to no list): this will destroy the list
 * you invoke it on.
 * </ul>
 *
 * @author Eric D. Friedman
 * @author Rob Eden
 * @author Jeff Randall
 * @version $Id: TLinkedList.java,v 1.1.2.3 2010/09/27 17:23:07 robeden Exp $
 * @see TLinkable
 */


public class TLinkedList<T extends TLinkable<T>>  extends AbstractSequentialList<T>
        implements Externalizable {

    static final long serialVersionUID = 1L;


    /** the head of the list */
    protected T _head;
    /** the tail of the list */
    protected T _tail;
    /** the number of elements in the list */
    protected int _size = 0;


    /** Creates a new <code>TLinkedList</code> instance. */
    public TLinkedList() {
        super();
    }


    /**
     * Returns an iterator positioned at <tt>index</tt>.  Assuming
     * that the list has a value at that index, calling next() will
     * retrieve and advance the iterator.  Assuming that there is a
     * value before <tt>index</tt> in the list, calling previous()
     * will retrieve it (the value at index - 1) and move the iterator
     * to that position.  So, iterating from front to back starts at
     * 0; iterating from back to front starts at <tt>size()</tt>.
     *
     * @param index an <code>int</code> value
     * @return a <code>ListIterator</code> value
     */
    public ListIterator<T> listIterator( int index ) {
        return new IteratorImpl( index );
    }


    /**
     * Returns the number of elements in the list.
     *
     * @return an <code>int</code> value
     */
    public int size() {
        return _size;
    }



    /**
     * Appends <tt>linkable</tt> to the end of the list.
     *
     * @param linkable an object of type TLinkable
     * @return always true
     */
    public boolean add( T linkable ) {
        if ( _size == 0 ) {
            _head = _tail = linkable; // first insertion
        } else
        {
            _tail.setNext( linkable );
            _tail = linkable;
        }
        _size++;
        return true;
    }


    /**
     * Inserts <tt>linkable</tt> at the head of the list.
     *
     * @param linkable an object of type TLinkable
     */


    /**
     * Adds <tt>linkable</tt> to the end of the list.
     *
     * @param linkable an object of type TLinkable
     */



    /**
     * Copies the list's contents into a native array.  This will be a
     * shallow copy: the Tlinkable instances in the Object[] array
     * have links to one another: changing those will put this list
     * into an unpredictable state.  Holding a reference to one
     * element in the list will prevent the others from being garbage
     * collected unless you clear the next/previous links.  <b>Caveat
     * programmer!</b>
     *
     * @return an <code>Object[]</code> value
     */
    public Object[] toArray() {
        Object[] o = new Object[_size];
        int i = 0;
        for ( TLinkable link = _head; link != null; link = link.getNext() ) {
            o[i++] = link;
        }
        return o;
    }


    /**
     * Copies the list to a native array, destroying the next/previous
     * links as the copy is made.  This list will be emptied after the
     * copy (as if clear() had been invoked).  The Object[] array
     * returned will contain TLinkables that do <b>not</b> hold
     * references to one another and so are less likely to be the
     * cause of memory leaks.
     *
     * @return an <code>Object[]</code> value
     */


    /**
     * Returns a typed array of the objects in the set.
     *
     * @param a an <code>Object[]</code> value
     * @return an <code>Object[]</code> value
     */


    /**
     * A linear search for <tt>o</tt> in the list.
     *
     * @param o an <code>Object</code> value
     * @return a <code>boolean</code> value
     */
    public boolean contains( Object o ) {
        for ( TLinkable<T> link = _head; link != null; link = link.getNext() ) {
            if ( o.equals( link ) ) {
                return true;
            }
        }
        return false;
    }


    /** {@inheritDoc} */
    @Override
    @SuppressWarnings({"unchecked"})
    public T get( int index ) {
        // Blow out for bogus values
        if ( index < 0 || index >= _size ) {
            throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + _size );
        }

        // Determine if it's better to get there from the front or the back

        int position = 0;
        T node = _head;

        while ( position < index ) {
            node = node.getNext();
            position++;
        }

        return node;

    }


    /**
     * Returns the head of the list
     *
     * @return an <code>Object</code> value
     */
    public T getFirst() {
        return _head;
    }


    /**
     * Returns the tail of the list.
     *
     * @return an <code>Object</code> value
     */
    public T getLast() {
        return _tail;
    }


    /**
     * Return the node following the given node. This method exists for two reasons:
     * <ol>
     * <li>It's really not recommended that the methods implemented by TLinkable be
     * called directly since they're used internally by this class.</li>
     * <li>This solves problems arising from generics when working with the linked
     * objects directly.</li>
     * </ol>
     * <p/>
     * NOTE: this should only be used with nodes contained in the list. The results are
     * undefined with anything else.
     *
     * @param current The current node
     * @return the node after the current node
     */
    @SuppressWarnings({"unchecked"})
    public T getNext( T current ) {
        return current.getNext();
    }





    public void writeExternal( ObjectOutput out ) throws IOException {
        // VERSION
        out.writeByte( 0 );

        // NUMBER OF ENTRIES
        out.writeInt( _size );

        // HEAD
        out.writeObject( _head );

        // TAIL
        out.writeObject( _tail );
    }


    @SuppressWarnings({"unchecked"})
    public void readExternal( ObjectInput in )
            throws IOException, ClassNotFoundException {

        // VERSION
        in.readByte();

        // NUMBER OF ENTRIED
        _size = in.readInt();

        // HEAD
        _head = (T) in.readObject();

        // TAIL
        _tail = (T) in.readObject();
    }


    /** A ListIterator that supports additions and deletions. */
    protected final class IteratorImpl implements ListIterator<T> {

        private int _nextIndex = 0;
        private T _next;
        private T _lastReturned;



        /**
         * Creates a new <code>Iterator</code> instance positioned at
         * <tt>index</tt>.
         *
         * @param position an <code>int</code> value
         */
        @SuppressWarnings({"unchecked"})
        IteratorImpl( int position ) {
            if ( position < 0 || position > _size ) {
                throw new IndexOutOfBoundsException();
            }

            _nextIndex = position;
            if ( position == 0 ) {
                _next = _head;
            } else if ( position == _size ) {
                _next = null;
            } else if ( position < ( _size >> 1 ) ) {
                int pos = 0;
                for ( _next = _head; pos < position; pos++ ) {
                    _next = _next.getNext();
                }

            }
        }


        /**
         * Insert <tt>linkable</tt> at the current position of the iterator.
         * Calling next() after add() will return the added object.
         *
         * @param linkable an object of type TLinkable
         */
        public final void add( T linkable ) {
            _lastReturned = null;
            _nextIndex++;

            if ( _size == 0 ) {
                TLinkedList.this.add( linkable );
            } else {
                TLinkedList.this.add( linkable );
            }
        }


        /**
         * True if a call to next() will return an object.
         *
         * @return a <code>boolean</code> value
         */
        public final boolean hasNext() {
            return _nextIndex != _size;
        }


        /**
         * True if a call to previous() will return a value.
         *
         * @return a <code>boolean</code> value
         */
        public final boolean hasPrevious() {
            return _nextIndex != 0;
        }

        @Override
        public T previous() {
            return null;
        }


        /**
         * Returns the value at the Iterator's index and advances the
         * iterator.
         *
         * @return an <code>Object</code> value
         * @throws NoSuchElementException if there is no next element
         */
        @SuppressWarnings({"unchecked"})
        public final T next() {
            if ( _nextIndex == _size ) {
                throw new NoSuchElementException();
            }

            _lastReturned = _next;
            _next = _next.getNext();
            _nextIndex++;
            return _lastReturned;
        }


        /**
         * returns the index of the next node in the list (the
         * one that would be returned by a call to next()).
         *
         * @return an <code>int</code> value
         */
        public final int nextIndex() {
            return _nextIndex;
        }


        /**
         * Returns the value before the Iterator's index and moves the
         * iterator back one index.
         *
         * @return an <code>Object</code> value
         * @throws NoSuchElementException if there is no previous element.
         */
        /**
         * Returns the previous element's index.
         *
         * @return an <code>int</code> value
         */
        public final int previousIndex() {
            return _nextIndex - 1;
        }


        /**
         * Removes the current element in the list and shrinks its
         * size accordingly.
         *
         * @throws IllegalStateException neither next nor previous
         *                               have been invoked, or remove or add have been invoked after
         *                               the last invocation of next or previous.
         */
        @SuppressWarnings({"unchecked"})
        public final void remove() {
            if ( _lastReturned == null ) {
                throw new IllegalStateException( "must invoke next or previous before invoking remove" );
            }

            if ( _lastReturned != _next ) {
                _nextIndex--;
            }
            _next = _lastReturned.getNext();
            TLinkedList.this.remove( _lastReturned );
            _lastReturned = null;
        }

        @Override
        public void set(T t) {

        }


        /**
         * Replaces the current element in the list with
         * <tt>linkable</tt>
         *
         * @param linkable an object of type TLinkable
         */


        /**
         * Replace from with to in the list.
         *
         * @param from a <code>TLinkable</code> value
         * @param to   a <code>TLinkable</code> value
         */
    }
} // TLinkedList
