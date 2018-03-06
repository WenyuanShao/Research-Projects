function [len_AC] = Length_AC (G,q)
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

%get all AC an d ready to code;
AC = [];
getac = @(block_struct) GetAC(block_struct.data);
AC_ = blockproc (B2,[8 8], getac);
AC_ = AC_';
AC = AC_(:);
AC = AC';

temp = Huffman_AC(AC);

for i = 1:length(temp)
    if (temp(i,2) == 0)
        AC_t(i,2) = temp(i,2);
    else
        temp(i,2) = abs(temp(i,2));
        AC_t(i,2) = floor(log2(temp(i,2)))+1;
    end
end

AC_t(:,1) = temp(:,1);
ac_set = [];
%p_code = dec2bin(240,8);
p_code = 240;
% for i = 1:101173
%     d = AC_t(i,1);
%     k = AC_t(i,2);
%     p = floor(d/15);
%     r = mod(d,15);
%     rk = [dec2bin(r,4),dec2bin(k,4)];
%     for j = 1:p
%         ac_set = [ac_set;p_code];
%     end
%     ac_set = [ac_set; rk];
% end

for i = 1:length(temp)
    d = AC_t(i,1);
    k = AC_t(i,2);
    p = floor(d/15);
    r = mod(d,15);
    rk = [dec2bin(r,4),dec2bin(k,4)];
    rk_dec = bin2dec(rk);
    for j = 1:p
        ac_set = [ac_set,p_code];
    end
    ac_set = [ac_set,rk_dec];
end
symbols = unique(ac_set);
[frequent,b] = histc(ac_set,symbols);
prob = frequent/length(ac_set);
dict = huffmandict(symbols,prob);
encode = huffmanenco(ac_set,dict);
% 
% %Caculate length
% len_sm = 0;
code_len = cellfun('length',dict(:,2));
len_sm = sum(AC_t(:,2));
len_k = length(encode);
len_dict = 8*length(dict(:,1)) + 5*length(dict(:,1));
for i = 1:length(dict(:,1))
    len_dict = len_dict + code_len(i);
end

len_AC = len_k + len_sm + len_dict;
