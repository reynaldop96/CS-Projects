from time import process_time
import sys
import random

def do_test(n):
    print ("Generating " + str(n) + " random numbers")
    a = [random.randint(0, 2**30-1) for i in range(n)]
    print("done...")
    result1 = insertionTest(a[:])   # we test on a copy of a
    result2 = mergeTest(a[:])
    result3= quickTest(a[:])
    result4= quick2Test (a[:])
    result5= quick3Test (a[:])
    check(result1, result2, result3, result4, result5)

def insertionTest(c):
    print("starting insertion test")
    t = process_time()
    insertionSort(c)
    print("Insertion sort took %f seconds" % ((process_time()-t)))
    return c

def mergeTest(c):
    print("starting mergesort test")
    t = process_time()
    mergeSort(c, 0, len(c)-1)
    print("Mergesort took %f seconds" % ((process_time()-t)))
    return c

##quickTest is the test for quicksort algorithm with fixed pivot on right
def quickTest (c):
    print ("starting quicksort right pivot test")
    t= process_time ()
    quickSort1(c,0,len(c)-1)
    print ("Quicksort right pivot took %f seconds" % ((process_time()-t)))
    return c

##quick2Test is the test for quicksort algorithm with random pivot
def quick2Test (c):
    print ("starting quicksort random pivot test")
    t = process_time()
    quickSort2 (c,0, len(c)-1)
    print ("Quicksort random pivot took %f seconds" % ((process_time()-t)))
    return c

##quick3Test is the test for quicksort algorithm with middle pivot
def quick3Test (c):
    print ("starting quicksort middle pivot test")
    t = process_time()
    quickSort3 (c,0, len(c)-1)
    print ("Quicksort middle pivot took %f seconds" % ((process_time()-t)))
    return c

## quickSort1 is quicksort with a fixed pivot on the right    
def quickSort1 (a, lo, hi):
    if lo > hi:
        return

    else:
        p = partition1 (a, lo, hi)
        quickSort1 (a, lo, p-1)
        quickSort1 (a, p+1, hi)


def partition1 (a, lo, hi):
    lesscount = lo
    pivot = a[hi]
    for i in range (lo, hi):
            if a[i] < pivot:
                temp = a[i]
                a[i] = a[lesscount]
                a[lesscount] = temp
                lesscount= lesscount +1
    temp = a[lesscount]
    a[lesscount] = pivot
    a[hi] = temp

    return lesscount

##quickSort2 is a quicksort with a randomly chosen pivot
def quickSort2 (a, lo, hi):
    if lo > hi:
        return

    else:
        p = partition2 (a, lo, hi)
        quickSort2 (a, lo, p-1)
        quickSort2 (a, p+1, hi)

def partition2 (a, lo, hi):
    lesscount = lo
    x = random.randint(lo,hi)
    pivot = a[x]
    
    for i in range (lo, x):
            if a[i] < pivot:
                temp = a[i]
                a[i] = a[lesscount]
                a[lesscount] = temp
                lesscount= lesscount +1
                
    for i in range (x+1, hi+1):
             if a[i] < pivot:
                temp = a[i]
                a[i] = a[lesscount]
                a[lesscount] = temp
                lesscount= lesscount +1

    x= a.index (pivot)
    a[x]= a[lesscount]
    a[lesscount] = pivot
                   
    return lesscount

##quicksort3 is quicksort with pivot in the middle
def quickSort3 (a, lo, hi):
    if lo > hi:
        return

    else:
        p = partition3 (a, lo, hi)
        quickSort3 (a, lo, p-1)
        quickSort3 (a, p+1, hi)

def partition3 (a, lo, hi):
    lesscount = lo
    x = (lo+hi)//2
    pivot = a[x]
    
    for i in range (lo, x):
            if a[i] < pivot:
                temp = a[i]
                a[i] = a[lesscount]
                a[lesscount] = temp
                lesscount= lesscount +1
                
    for i in range (x+1, hi+1):
             if a[i] < pivot:
                temp = a[i]
                a[i] = a[lesscount]
                a[lesscount] = temp
                lesscount= lesscount +1

    x= a.index (pivot)
    a[x]= a[lesscount]
    a[lesscount] = pivot
                   
    return lesscount

##mergeSort algorithm
def mergeSort (a, lo, hi):
    if hi <= lo:
        return
    else:
        mid = (lo+hi)//2
        mergeSort (a, lo, mid)
        mergeSort (a,mid+1, hi)
        merge (a, lo, mid, mid +1, hi)

    return a

def merge (a, lo1, hi1, lo2, hi2):
    b=[]
    p1= lo1
    p2= lo2

    while p1<= hi1 and p2 <= hi2:
        if a[p1]<a[p2]:
            b.append (a[p1])
            p1=p1+1
        else:
            b.append(a[p2])
            p2=p2+1

    while p1<= hi1:
        b.append (a[p1])
        p1=p1+1
        
    while p2<= hi2:
        b.append (a[p2])
        p2=p2+1

    
    for i in range (len(b)):
        a[lo1+ i]= b[i]

b=[]
for i in range (len(c)):
    b.append (0)

def mergeSort (a, lo, hi):
    if hi <= lo:
        return
    else:
        mid = (lo+hi)//2
        mergeSort (a, lo, mid)
        mergeSort (a,mid+1, hi)
        merge (a, lo, mid, mid +1, hi, b)

    return a

def merge (a, lo1, hi1, lo2, hi2, b):
    p1= lo1
    p2= lo2
    k=0

    while p1<= hi1 and p2 <= hi2:
        if a[p1]<a[p2]:
            b[k]= a[p1]
            p1=p1+1
            k=k+1
        else:
            b[k]= a[p2]
            p2=p2+1
            k=k+1

    while p1<= hi1:
        b[k]= a[p1]
        p1=p1+1
        k=k+1
        
    while p2<= hi2:
        b[k]= a[p2]
        p2=p2+1
        k=k+1

    for i in range (len(b)):
        a[lo1+ i]= b[i]


def insertionSort(a):
    n = len(a)
    for i in range(1,n):
        t = a[i]
        j = i-1
        while j>=0 and t<a[j]:
            a[j+1]=a[j]
            j -= 1
        a[j+1]=t
                
def check(a, b, c, d, e):       # verify that two arrays are equal
    if a!=b or a!=c or a!= d or a!=e:
        raise Error("Sorted arrays don't match")
    
if __name__=="__main__":
    if len(sys.argv) >= 2:
        n = int(sys.argv[1])
    else:
        n = int(input("Enter size: "))
    do_test(n)
    
