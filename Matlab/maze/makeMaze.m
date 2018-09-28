function arr = makeMaze(size)
    wallChar = '#';
    goalChar = 'X';
    strtChar = 'O';
    pathChar = ' ';
    if length(size) ~= 2 || any(size <= 2)
        error('Size must be a 1x2 vector larger than [2 2]')
    end 
    maze = char(wallChar.*ones(size));
    weights = reshape(randperm(prod(size)),size);
    [rs,cs] = find(weights == min(min([weights([1 end],2:end-1) weights(2:end-1,[1 end])')));
    maze(rs,cs) = pathChar;
    
end

