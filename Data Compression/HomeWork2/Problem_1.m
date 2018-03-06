input_data = 'babacaab';
input_data = abs(input_data);
len = length(input_data);
matrix = [];
it = 1;

for i=1:len
    matrix_data = [input_data(1:end),it];
    matrix = [matrix;matrix_data];
    input_data = [input_data(2:end),input_data(1)];
    it = it+1;
end

matrix = sortrows(matrix);
for i=1:len
    if matrix(i,len+1) == 1
        result_X = i;
    end
end
matrix_char = char(matrix)
result_y = char(matrix(:,len));