clear all;
[I,map] = imread('/Users/shaowenyuan/Desktop/river.gif');
G = ind2gray(I,map);
G = double(G);

q = 3.18;

[len_DC,B2] = Length_DC(G,q);
len_AC = Length_AC(G,q);
 
len = len_DC + len_AC;

com_ratio = (632*848*8)/len;
Q =[16  11  10  16  24  40  51  61
    12  12  14  19  26  58  60  55
    14  13  16  24  40  57  69  56
    14  17  22  29  51  87  80  62
    18  22  37  56  68  109 103 77
    24  35  55  64  81  104 113 92
    49  64  78  87  103 121 120 101
    72  92  95  98  112 100 103 99];

Q = Q*q;
DeQuantization = @(block_struct) (block_struct.data) .* Q;
B1 = blockproc (B2,[8 8], DeQuantization);

idct = @(block_struct) idct2(block_struct.data);
G_ = blockproc(B1,[8,8],idct);
G_ = G_+128;

SNR_ = snr(G,abs(G-G_));
image(G_);

Scalars            = [0.5                1                  1.5                 2                  2.5                3                  3.5                4];
SNR                = [25.928669786357688 23.653082707186940 22.496391264997296  21.723028909901190 21.118862517180258 20.619376149367774 20.186113093128970 19.812908478502482];
compression_ratio  = [5.208299775389546  8.217561385115774  10.990346411561749  13.622663091137857 16.311973307259468 19.065585798711318 21.797424465040137 24.661003008219400];
%plot(Scalars,compression_ratio);

