[I,map] = imread('/Users/shaowenyuan/Desktop/river.gif');
G = ind2gray(I,map);
E = entropy(G);
G = double(G);

% %Uniform Quantizer
% %-------------------------------------------------------------------------%
% [MAX, index_max] = max(G(:));
% [MIN, index_min] = min(G(:));
% delta = (243 + 0.001 - 7)/6;
% 
% D = [];
% D(1) = MIN;
% 
% for i = 2:7
%     D(i) = D(i-1) + delta;
% end
% 
% [n,QG] = histc(G,D);
% QG = QG - 1;
% 
% QG_ = uint8(QG);
% E_QG = entropy(QG_);
% 
% R = [];
% for i = 1:6
%     R(i) = (D(i) + D(i+1))/2;
% end
% 
% DQG = [];
% [row,column] = size(QG);
% for i = 1:row
%     for j = 1:column
%         DQG(i,j) = R(QG(i,j)+1);
%     end
% end
% 
% image(DQG);
% SNR = snr(G,G-DQG);

% Semi-Uniform Quantizer && Max-Loyd Quantizer
% -------------------------------------------------------------------------%
for j = 1:10
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
QG_ = uint8(QG);
E_QG = entropy(QG_);
DQG = [];
[row,column] = size(QG);
for i = 1:row
    for j = 1:column
        DQG(i,j) = R(QG(i,j)+1);
    end
end
imshow(DQG,map);
SNR = snr(G,G-DQG);





