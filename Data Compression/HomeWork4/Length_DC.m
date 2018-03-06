function [len_DC,B2] = len_DC (G,q)
% [I,map] = imread('/Users/shaowenyuan/Desktop/river.gif');
% G = ind2gray(I,map);
% G = double(G);

G = G-128;
dct = @(block_struct) dct2(block_struct.data);
B = blockproc(G,[8,8],dct);

Q =[16  11  10  16  24  40  51  61
    12  12  14  19  26  58  60  55
    14  13  16  24  40  57  69  56
    14  17  22  29  51  87  80  62
    18  22  37  56  68  109 103 77
    24  35  55  64  81  104 113 92
    49  64  78  87  103 121 120 101
    72  92  95  98  112 100 103 99];
Q = Q*q;
Quantization = @(block_struct) (block_struct.data) ./ Q;
B1 = blockproc (B,[8 8], Quantization);
B2 = round(B1);

DC = [];
[row,column] = size(B2);

row = row/8;
column = column/8;
count = 1;

for i = 1:row
    for j = 1:column
        DC(count) = B2(1+8*(i-1),1+8*(j-1));
        count = count+1;
    end
end

%Code DC terms
[row,col] = size(DC);

%Apply DPCM
DC_1=[];
[rows,cols]=size(DC);
DC_1(1,1)=0;

for i=1:rows
    for j=2:cols
        DC_1(i,j)=DC(i,j-1);
    end
end

DC=DC_1-DC;

%Code the residuals
DC = abs(DC);
len = length(DC);
for i = 1:len
    if (DC(1,i) == 0)           %if DC == 0, there will be no such k. So let the k=0, when DC==0; 
        k(1,i) = 0;
    else
        k(1,i) = floor(log2(DC(1,i)))+1;
    end
end

symbols = [0 1 2 3 4 5 6 7 8 9 10 11];
[frequent,b] = histc(k,symbols);
prob = frequent/8374;
dict = huffmandict(symbols,prob);
encode = huffmanenco(k,dict);

%Caculate length
len_sm = 0;
code_len = cellfun('length',dict(:,2));
for i = 1:12
    len_sm = len_sm + frequent(i)*code_len(i);
end
len_k = length(encode);
len_dict = 4*12 + 4*12;
for i = 1:12
    len_dict = len_dict + code_len(i);
end

len_DC = len_k + len_sm + len_dict;
end









