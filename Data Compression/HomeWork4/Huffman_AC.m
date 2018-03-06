function [temp] = Huffman_AC (AC)

temp = [];
[row,column] = size(AC);
it = 1;
count = 0;

for i = 1:column
    if it > 63
        string = [0,0];
        temp = [temp;string];
        it = 1;
        count = 0;
    end
    if AC(i) ~= 0
        string = [count,AC(i)];
        temp = [temp;string];
        count = 0;
        it = it+1;
    else
        count = count+1;
        it = it+1;
    end     
end