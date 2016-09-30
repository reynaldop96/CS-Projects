""" An implementation of heapsort. """
#This algorithm aims at comparing heapsort time complexity with that of insertion sort.

import sys
import random
import time

#This is the modified heapify version to include a branches input
def heapify(heap, index, size, branch):
    child1= index *branch +1
    #check if any children exist
    if child1< size:
        #get index of largest child
        largest = child1
        for i in range (1, branch):
            if (child1 + i) <size and heap [largest]< heap [child1 +i]:
                largest = (child1 + i)
        
        # check if value at index is < largest child
        if heap[index] < heap[largest]:
            # swap value down and recurse at the location we swapped into
            swap(heap, index, largest)
            heapify(heap, largest, size, branch)

def swap(heap, i, j):
    temp = heap[i]
    heap[i] = heap[j]
    heap[j] = temp

#This is a modified build_heap to include branches input 
def build_heap(heap, branch):
    # call heapify starting from the last node that has children and going
    # backwards
    i = (len(heap) -1)// branch 
    while i >= 0:
        heapify(heap, i, len(heap), branch)
        i = i - 1

def heapsort_test(c, branch):
    print("starting heapsort test")
    t = time.process_time()
    heapsort(c, branch)
    print("Heapsort took %f seconds" % ((time.process_time()-t)))
    return c

def heapsort(arr, branch):
    #we first build a heap 
    build_heap(arr,branch)
    i= len(arr)- 1
    #we get the largest element of the heap
    #put this element in the corresponding right-most position
    #then heapify the non-sorted part of the array to get largest element again
    while i >0:
        swap (arr, 0, i)
        i= i-1
        heapify(arr, 0, i+1, branch)
            
def insertion_test(c):
    print("starting insertion test")
    t = time.process_time()
    insertion_sort(c)
    print("Insertion sort took %f seconds" % ((time.process_time()-t)))
    return c

def insertion_sort(a):
    n = len(a)
    for i in range(1,n):
        t = a[i]
        j = i-1
        while j>=0 and t<a[j]:
            a[j+1]=a[j]
            j -= 1
        a[j+1]=t
                
def check(a, b):       # verify that two arrays are equal
    if a!=b:
        raise Exception("Sorted arrays don't match")

def do_test(n, branch):
    print ("Generating " + str(n) + " random numbers")
    a = [random.randint(0, 2**30-1) for i in range(n)]
    t = time.process_time()
    hs_result = heapsort_test(a[:], branch)
    is_result = insertion_test(a[:])
    check(hs_result, is_result)

if __name__=="__main__":
    if len(sys.argv) >= 3:
        n = int(sys.argv[1])
        branch = int(sys.argv[2])
    else:
        n = int(input("Enter size: "))
        branch = int(input("Enter branching factor: "))
    do_test(n, branch)
