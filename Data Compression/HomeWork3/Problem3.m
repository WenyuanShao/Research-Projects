[I,map] = imread('/Users/shaowenyuan/Desktop/river.gif');
G = ind2gray(I,map);
E = entropy(I);
G = double(G);

parameter = [1,0,0;0,0,1;0.5,0,0.5;1,-1,1;0.75,-0.5,0.75];
R = [];
R(1,1) = G(1,1);
[row,column] = size(G);

Number = 1;

for j = 2:column
    R(1,j) = G(1,j) - G(1,j-1);
end

for i = 2:row
    R(i,1) = G(i,1) - G(i-1,1);
end

for i = 2:row
    for j = 2:column
        R(i,j) = floor(G(i,j)-(parameter(Number,1)*G(i,j-1) + parameter(Number,2)*G(i-1,j-1) + parameter(Number,3)*G(i-1,j)));
    end
end

E = entropy(R);
%imshow(R,map);
GG = G;
G = R;

[MAX, index_max] = max(G(:));
[MIN, index_min] = min(G(:));
delta = (MAX + 0.001 - MIN)/6;

D = [];
D(1) = MIN;

for i = 2:7
    D(i) = D(i-1) + delta;
end

for j = 1:20
    R = [0,0,0,0,0,0];
    count = [0,0,0,0,0,0];
    [row,column] = size(G);
    for i = 1:row
        for k = 1:column
            if G(i,k) >= D(1) && G(i,k) < D(2)
                R(1) = R(1) + G(i,k);
                count(1) = count(1) + 1;
            end
            if G(i,k) >= D(2) && G(i,k) < D(3)
                R(2) = R(2) + G(i,k);
                count(2) = count(2) + 1;
            end
            if G(i,k) >= D(3) && G(i,k) < D(4)
                R(3) = R(3) + G(i,k);
                count(3) = count(3) + 1;
            end
            if G(i,k) >= D(4) && G(i,k) < D(5)
                R(4) = R(4) + G(i,k);
                count(4) = count(4) + 1;
            end
            if G(i,k) >= D(5) && G(i,k) < D(6)
                R(5) = R(5) + G(i,k);
                count(5) = count(5) + 1;
            end
            if G(i,k) >= D(6) && G(i,k) < D(7)
                R(6) = R(6) + G(i,k);
                count(6) = count(6) + 1;
            end
        end
    end

    for i = 1:length(R)
        R(i) = R(i)/count(i);
    end

    for i = 2:length(D)-1
        D(i) = (R(i-1) + R(i))/2;
    end
end

[n,QG] = histc(G,D);
QG = QG - 1;
E_QG = entropy(QG);
DQG = [];
[row,column] = size(QG);
for i = 1:row
    for j = 1:column
        DQG(i,j) = R(QG(i,j)+1);
    end
end
imshow(DQG,map);

ML_E = reshape(QG,1,row*column);


ind = 1;
d(ind) = ML_E(1);
c(ind) = 1;

for i=2 :length(ML_E)
    if ML_E(i-1)==ML_E(i)
       c(ind)=c(ind)+1;
    else ind = ind+1;
         d(ind)=ML_E(i);
         c(ind)=1;
    end
end

bitstream = [d;c];
bitstream = reshape(bitstream,1,292471*2);

bit_ = uint8(bitstream);
E_RLE = entropy(bit_);

g=[];
g(1,1) = DQG(1,1);
for j = 2:column
    g(1,j) = g(1,j-1) + DQG(1,j);
end

for i = 2:row
    g(i,1) = g(i-1,1) + DQG(i,1);
end

for i = 2:row
    for j = 2:column
        g(i,j) = parameter(Number,1)*g(i,j-1) + parameter(Number,2)*g(i-1,j-1) + parameter(Number,3)*g(i-1,j) + DQG(i,j);
    end
end

imshow (g,map);
SNR = snr(GG,GG-g);








