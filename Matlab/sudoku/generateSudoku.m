function arr = generateSudoku()%difficulty)
    
    arr = randBoard;



end

function arr = randBoard()
    arr = NaN.*ones(9);
    for i = 1:9
        for j = 1:9
            possible = 1:9;
            colvec = arr(:,j)';
            rowvec = arr(i,:);
            boxvec = boxer(arr,i,j);
            for k = 9:-1:1
                if any(k == colvec) || any(k == rowvec) || any(k == boxvec)
                    possible(k) = [];
                end
            end
            arr(i,j) = possible(ceil(rand.*length(possible)));
        end
    end
    
end

function log = isBoardUnique(arr)
    
    
    
end

function out = boxer(in,i,j)
    switch i
        case {1,2,3}
            row = [1 2 3];
        case {4,5,6}
            row = [4 5 6];
        case {7,8,9}
            row = [7 8 9];
    end
    switch j
        case {1,2,3}
            col = [1 2 3];
        case {4,5,6}
            col = [4 5 6];
        case {7,8,9}
            col = [7 8 9];
    end
    out = in(row,col);
    out = out(:)';
end