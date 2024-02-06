/**
 * Represnts a list of musical tracks. The list has a maximum capacity (int),
 * and an actual size (number of tracks in the list, an int).
 */
class PlayList {
    private Track[] tracks; // Array of tracks (Track objects)
    private int maxSize; // Maximum number of tracks in the array
    private int size; // Actual number of tracks in the array

    /** Constructs an empty play list with a maximum number of tracks. */
    public PlayList(int maxSize) {
        this.maxSize = maxSize;
        tracks = new Track[maxSize];
        size = 0;
    }

    /** Returns the maximum size of this play list. */
    public int getMaxSize() {
        return this.maxSize;
    }

    /** Returns the current number of tracks in this play list. */
    public int getSize() {
        return this.size;
    }

    /** Method to get a track by index */
    public Track getTrack(int index) {
        if (index >= 0 && index < size) {
            return tracks[index];
        } else {
            return null;
        }
    }

    /**
     * Appends the given track to the end of this list.
     * If the list is full, does nothing and returns false.
     * Otherwise, appends the track and returns true.
     */
    public boolean add(Track track) {
        if (this.size >= this.maxSize)
            return false;
        this.tracks[size + 1] = track;
        this.size++;
        return true;

    }

    /**
     * Returns the data of this list, as a string. Each track appears in a separate
     * line.
     */
    //// For an efficient implementation, use StringBuilder.
    public String toString() {
        String string = "";
        for (int i = 0; i < size; i++) {
            string += this.getTrack(i).toString() + "\n";
        }
        return string;
    }

    /**
     * Removes the last track from this list. If the list is empty, does nothing.
     */
    public void removeLast() {
        if (this.size > 0) {
            Track[] newtracks = new Track[this.getSize() - 1];
            for (int i = 0; i < newtracks.length; i++) {
                newtracks[i] = this.tracks[i];
            }
            this.tracks = newtracks;
            this.size--;
        }
    }

    /** Returns the total duration (in seconds) of all the tracks in this list. */
    public int totalDuration() {
        int total = 0;
        for (int i = 0; i < this.getSize(); i++) {
            if (this.getTrack(i) != null) {
                total += this.getTrack(i).getDuration();
            }
        }
        return total;
    }

    /**
     * Returns the index of the track with the given title in this list.
     * If such a track is not found, returns -1.
     */
    public int indexOf(String title) {
        for (int i = 0; i < this.getSize(); i++) {
            if (this.getTrack(i) != null) {
                if (this.getTrack(i).getTitle().toLowerCase() == title.toLowerCase())
                    return i;
            }
        }
        return -1;
    }

    /**
     * Inserts the given track in index i of this list. For example, if the list is
     * (t5, t3, t1), then just after add(1,t4) the list becomes (t5, t4, t3, t1).
     * If the list is the empty list (), then just after add(0,t3) it becomes (t3).
     * If i is negative or greater than the size of this list, or if the list
     * is full, does nothing and returns false. Otherwise, inserts the track and
     * returns true.
     */
    public boolean add(int i, Track track) {
        if (i < 0 || i > this.getSize() || this.getSize() == this.getMaxSize())
            return false;
        if (i == 0)
            this.tracks[i] = track;
        else {
            for (int j = this.getSize(); j > i; j--) {
                this.tracks[j] = this.tracks[j - 1];
            }
            this.tracks[i] = track;
        }
        this.size++;
        return true;
    }

    /**
     * Removes the track in the given index from this list.
     * If the list is empty, or the given index is negative or too big for this
     * list,
     * does nothing and returns -1.
     */
    public void remove(int i) {
        if (this.getSize() != 0 && i >= 0 && i < this.getSize()) {
            for (int j = i; j < this.getSize() - 1; j++) {
                this.tracks[j] = this.tracks[j + 1];
            }
            // this.tracks[size - 1] = new Track("", "", 0);
            this.size--;
        }
    }

    /**
     * Removes the first track that has the given title from this list.
     * If such a track is not found, or the list is empty, or the given index
     * is negative or too big for this list, does nothing.
     */
    public void remove(String title) {
        int index = this.indexOf(title);
        if (index != -1 && this.getSize() != 0 && index >= 0 && index < this.getSize())
            remove(index);
    }

    /**
     * Removes the first track from this list. If the list is empty, does nothing.
     */
    public void removeFirst() {
        if (this.getSize() > 0)
            remove(0);
    }

    /**
     * Adds all the tracks in the other list to the end of this list.
     * If the total size of both lists is too large, does nothing.
     */
    //// An elegant and terribly inefficient implementation.
    public void add(PlayList other) {

        int newSize = this.getSize() + other.getSize();
        if (newSize <= this.getMaxSize()) {
            while (other.getSize() != 0) {
                this.add(other.tracks[0]);
                other.removeFirst();
                ;
            }
        }
    }

    /**
     * Returns the index in this list of the track that has the shortest duration,
     * starting the search in location start. For example, if the durations are
     * 7, 1, 6, 7, 5, 8, 7, then min(2) returns 4, since this the index of the
     * minimum value (5) when starting the search from index 2.
     * If start is negative or greater than size - 1, returns -1.
     */
    private int minIndex(int start) {
        if (start < 0 || start > this.getSize() - 1)
            return -1;
        int index = start;
        for (int i = start; i < this.getSize(); i++) {
            if (this.tracks[i].isShorterThan(this.tracks[index]))
                index = i;
        }
        return index;
    }

    /**
     * Returns the title of the shortest track in this list.
     * If the list is empty, returns null.
     */
    public String titleOfShortestTrack() {
        if (this.getSize() == 0)
            return null;
        return tracks[minIndex(0)].getTitle();
    }

    /**
     * Sorts this list by increasing duration order: Tracks with shorter
     * durations will appear first. The sort is done in-place. In other words,
     * rather than returning a new, sorted playlist, the method sorts
     * the list on which it was called (this list).
     */
    public void sortedInPlace() {
        // Uses the selection sort algorithm,
        // calling the minIndex method in each iteration.
        //// replace this statement with your code
        for (int i = 0; i < this.getSize(); i++) {
            int min = i;
            for (int k = i + 1; k < this.getSize(); k++) {
                if (this.tracks[k].isShorterThan(this.tracks[i]))
                    min = k;
            }
            Track temp = tracks[i];
            this.tracks[i] = this.tracks[min];
            this.tracks[min] = temp;
        }

    }
}
