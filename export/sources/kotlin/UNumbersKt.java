package kotlin;

import androidx.constraintlayout.widget.ConstraintLayout;

/* JADX INFO: compiled from: UNumbers.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b)\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0004\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0007\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\n\u001a\u0017\u0010\u000e\u001a\u00020\u0001*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\r\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0004\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0007\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\n\u001a\u0017\u0010\u0013\u001a\u00020\u0001*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\r\u001a\u001f\u0010\u0018\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001b\u001a\u001f\u0010\u0018\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001f\u0010\u0018\u001a\u00020\b*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001f\u0010\u0018\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001f\u0010\"\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b#\u0010\u001b\u001a\u001f\u0010\"\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b$\u0010\u001d\u001a\u001f\u0010\"\u001a\u00020\b*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b%\u0010\u001f\u001a\u001f\u0010\"\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0004\b&\u0010!\u001a\u0017\u0010'\u001a\u00020\u0002*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010)\u001a\u0017\u0010'\u001a\u00020\u0005*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0007\u001a\u0017\u0010'\u001a\u00020\b*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u0017\u0010'\u001a\u00020\u000b*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u0017\u0010/\u001a\u00020\u0002*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b0\u0010)\u001a\u0017\u0010/\u001a\u00020\u0005*\u00020\u0005H\u0087\bø\u0001\u0000¢\u0006\u0004\b1\u0010\u0007\u001a\u0017\u0010/\u001a\u00020\b*\u00020\bH\u0087\bø\u0001\u0000¢\u0006\u0004\b2\u0010,\u001a\u0017\u0010/\u001a\u00020\u000b*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0004\b3\u0010.\u0082\u0002\u0004\n\u0002\b\u0019¨\u00064"}, d2 = {"countLeadingZeroBits", "", "Lkotlin/UByte;", "countLeadingZeroBits-7apg3OU", "(B)I", "Lkotlin/UInt;", "countLeadingZeroBits-WZ4Q5Ns", "(I)I", "Lkotlin/ULong;", "countLeadingZeroBits-VKZWuLQ", "(J)I", "Lkotlin/UShort;", "countLeadingZeroBits-xj2QHRw", "(S)I", "countOneBits", "countOneBits-7apg3OU", "countOneBits-WZ4Q5Ns", "countOneBits-VKZWuLQ", "countOneBits-xj2QHRw", "countTrailingZeroBits", "countTrailingZeroBits-7apg3OU", "countTrailingZeroBits-WZ4Q5Ns", "countTrailingZeroBits-VKZWuLQ", "countTrailingZeroBits-xj2QHRw", "rotateLeft", "bitCount", "rotateLeft-LxnNnR4", "(BI)B", "rotateLeft-V7xB4Y4", "(II)I", "rotateLeft-JSWoG40", "(JI)J", "rotateLeft-olVBNx4", "(SI)S", "rotateRight", "rotateRight-LxnNnR4", "rotateRight-V7xB4Y4", "rotateRight-JSWoG40", "rotateRight-olVBNx4", "takeHighestOneBit", "takeHighestOneBit-7apg3OU", "(B)B", "takeHighestOneBit-WZ4Q5Ns", "takeHighestOneBit-VKZWuLQ", "(J)J", "takeHighestOneBit-xj2QHRw", "(S)S", "takeLowestOneBit", "takeLowestOneBit-7apg3OU", "takeLowestOneBit-WZ4Q5Ns", "takeLowestOneBit-VKZWuLQ", "takeLowestOneBit-xj2QHRw", "kotlin-stdlib"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class UNumbersKt {
    /* JADX INFO: renamed from: countOneBits-WZ4Q5Ns, reason: not valid java name */
    private static final int m428countOneBitsWZ4Q5Ns(int i) {
        return Integer.bitCount(i);
    }

    /* JADX INFO: renamed from: countLeadingZeroBits-WZ4Q5Ns, reason: not valid java name */
    private static final int m424countLeadingZeroBitsWZ4Q5Ns(int i) {
        return Integer.numberOfLeadingZeros(i);
    }

    /* JADX INFO: renamed from: countTrailingZeroBits-WZ4Q5Ns, reason: not valid java name */
    private static final int m432countTrailingZeroBitsWZ4Q5Ns(int i) {
        return Integer.numberOfTrailingZeros(i);
    }

    /* JADX INFO: renamed from: takeHighestOneBit-WZ4Q5Ns, reason: not valid java name */
    private static final int m444takeHighestOneBitWZ4Q5Ns(int i) {
        return UInt.m271constructorimpl(Integer.highestOneBit(i));
    }

    /* JADX INFO: renamed from: takeLowestOneBit-WZ4Q5Ns, reason: not valid java name */
    private static final int m448takeLowestOneBitWZ4Q5Ns(int i) {
        return UInt.m271constructorimpl(Integer.lowestOneBit(i));
    }

    /* JADX INFO: renamed from: rotateLeft-V7xB4Y4, reason: not valid java name */
    private static final int m436rotateLeftV7xB4Y4(int i, int i2) {
        return UInt.m271constructorimpl(Integer.rotateLeft(i, i2));
    }

    /* JADX INFO: renamed from: rotateRight-V7xB4Y4, reason: not valid java name */
    private static final int m440rotateRightV7xB4Y4(int i, int i2) {
        return UInt.m271constructorimpl(Integer.rotateRight(i, i2));
    }

    /* JADX INFO: renamed from: countOneBits-VKZWuLQ, reason: not valid java name */
    private static final int m427countOneBitsVKZWuLQ(long j) {
        return Long.bitCount(j);
    }

    /* JADX INFO: renamed from: countLeadingZeroBits-VKZWuLQ, reason: not valid java name */
    private static final int m423countLeadingZeroBitsVKZWuLQ(long j) {
        return Long.numberOfLeadingZeros(j);
    }

    /* JADX INFO: renamed from: countTrailingZeroBits-VKZWuLQ, reason: not valid java name */
    private static final int m431countTrailingZeroBitsVKZWuLQ(long j) {
        return Long.numberOfTrailingZeros(j);
    }

    /* JADX INFO: renamed from: takeHighestOneBit-VKZWuLQ, reason: not valid java name */
    private static final long m443takeHighestOneBitVKZWuLQ(long j) {
        return ULong.m350constructorimpl(Long.highestOneBit(j));
    }

    /* JADX INFO: renamed from: takeLowestOneBit-VKZWuLQ, reason: not valid java name */
    private static final long m447takeLowestOneBitVKZWuLQ(long j) {
        return ULong.m350constructorimpl(Long.lowestOneBit(j));
    }

    /* JADX INFO: renamed from: rotateLeft-JSWoG40, reason: not valid java name */
    private static final long m434rotateLeftJSWoG40(long j, int i) {
        return ULong.m350constructorimpl(Long.rotateLeft(j, i));
    }

    /* JADX INFO: renamed from: rotateRight-JSWoG40, reason: not valid java name */
    private static final long m438rotateRightJSWoG40(long j, int i) {
        return ULong.m350constructorimpl(Long.rotateRight(j, i));
    }

    /* JADX INFO: renamed from: countOneBits-7apg3OU, reason: not valid java name */
    private static final int m426countOneBits7apg3OU(byte b) {
        return Integer.bitCount(UInt.m271constructorimpl(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: countLeadingZeroBits-7apg3OU, reason: not valid java name */
    private static final int m422countLeadingZeroBits7apg3OU(byte b) {
        return Integer.numberOfLeadingZeros(b & UByte.MAX_VALUE) - 24;
    }

    /* JADX INFO: renamed from: countTrailingZeroBits-7apg3OU, reason: not valid java name */
    private static final int m430countTrailingZeroBits7apg3OU(byte b) {
        return Integer.numberOfTrailingZeros(b | UByte.MIN_VALUE);
    }

    /* JADX INFO: renamed from: takeHighestOneBit-7apg3OU, reason: not valid java name */
    private static final byte m442takeHighestOneBit7apg3OU(byte b) {
        return UByte.m194constructorimpl((byte) Integer.highestOneBit(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: takeLowestOneBit-7apg3OU, reason: not valid java name */
    private static final byte m446takeLowestOneBit7apg3OU(byte b) {
        return UByte.m194constructorimpl((byte) Integer.lowestOneBit(b & UByte.MAX_VALUE));
    }

    /* JADX INFO: renamed from: rotateLeft-LxnNnR4, reason: not valid java name */
    private static final byte m435rotateLeftLxnNnR4(byte b, int i) {
        return UByte.m194constructorimpl(NumbersKt.rotateLeft(b, i));
    }

    /* JADX INFO: renamed from: rotateRight-LxnNnR4, reason: not valid java name */
    private static final byte m439rotateRightLxnNnR4(byte b, int i) {
        return UByte.m194constructorimpl(NumbersKt.rotateRight(b, i));
    }

    /* JADX INFO: renamed from: countOneBits-xj2QHRw, reason: not valid java name */
    private static final int m429countOneBitsxj2QHRw(short s) {
        return Integer.bitCount(UInt.m271constructorimpl(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: countLeadingZeroBits-xj2QHRw, reason: not valid java name */
    private static final int m425countLeadingZeroBitsxj2QHRw(short s) {
        return Integer.numberOfLeadingZeros(s & UShort.MAX_VALUE) - 16;
    }

    /* JADX INFO: renamed from: countTrailingZeroBits-xj2QHRw, reason: not valid java name */
    private static final int m433countTrailingZeroBitsxj2QHRw(short s) {
        return Integer.numberOfTrailingZeros(s | UShort.MIN_VALUE);
    }

    /* JADX INFO: renamed from: takeHighestOneBit-xj2QHRw, reason: not valid java name */
    private static final short m445takeHighestOneBitxj2QHRw(short s) {
        return UShort.m457constructorimpl((short) Integer.highestOneBit(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: takeLowestOneBit-xj2QHRw, reason: not valid java name */
    private static final short m449takeLowestOneBitxj2QHRw(short s) {
        return UShort.m457constructorimpl((short) Integer.lowestOneBit(s & UShort.MAX_VALUE));
    }

    /* JADX INFO: renamed from: rotateLeft-olVBNx4, reason: not valid java name */
    private static final short m437rotateLeftolVBNx4(short s, int i) {
        return UShort.m457constructorimpl(NumbersKt.rotateLeft(s, i));
    }

    /* JADX INFO: renamed from: rotateRight-olVBNx4, reason: not valid java name */
    private static final short m441rotateRightolVBNx4(short s, int i) {
        return UShort.m457constructorimpl(NumbersKt.rotateRight(s, i));
    }
}
