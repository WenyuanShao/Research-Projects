[I,map] = imread('/Users/shaowenyuan/Desktop/river.gif');
G = ind2gray(I,map);
G = double(G);

dct = @(block_struct) dct2(block_struct.data);
B = blockproc(G,[8,8],dct);

temp = [];
[row,column] = size(G);

row = row/8;
column = column/8;

for i = 1:row
    for j = 1:column
        temp(i,j) = B(1+8*(i-1),1+8*(j-1));
    end
end

[temp2,QG] = UniformTransform(16,temp);


TF = @(block_struct) UT(8,block_struct.data);
B2 = blockproc(B,[8,8],TF);

mask = [0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0];
B3 = blockproc(B2,[8 8],@(block_struct) mask .* block_struct.data);


for i = 1:row
    for j = 1:column
        B3(1+8*(i-1),1+8*(j-1)) = temp2(i,j);
    end
end


invdct = @(block_struct) idct2(block_struct.data);
DCT_res = blockproc(B3,[8 8],invdct);
DCT_res_ = uint8(DCT_res);
E = entropy(DCT_res_);
image(DCT_res);
SNR = snr(G,G-DCT_res);









