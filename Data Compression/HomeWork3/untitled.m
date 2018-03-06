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

temp8 = [];
count = 1;
for i = 1:row*8
    for j = 1:column*8
        if mod(i,8) == 1 & mod(j,8) == 1
            continue;
        end
        temp8(count) = B(i,j);
        count = count+1;
    end
end

[D,R] = getPara(16,temp8);

QG_1 = UniformTransform2(16,B,D,R);

for i = 1:row
    for j = 1:column
        QG_1(1+8*(i-1),1+8*(j-1)) = QG(i,j);
    end
end
mask = [1   1   1   1   0   0   0   0
        1   1   1   0   0   0   0   0
        1   1   0   0   0   0   0   0
        1   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0
        0   0   0   0   0   0   0   0];
QG_1 = blockproc(QG_1,[8 8],@(block_struct) mask .* block_struct.data);

invdct = @(block_struct) idct2(block_struct.data);
DCT_res = blockproc(QG_1,[8 8],invdct);
DCT_res_ = uint8(DCT_res);
E = entropy(DCT_res_);
image(DCT_res);
SNR = snr(G,G-DCT_res);

ratio = 

