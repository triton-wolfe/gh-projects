function out = determinate(in)
    [r,c] = size(in);
    if r == c
        out = 0;
        if r > 1
            for i = 1:r
                inp = in(2:end,:);
                inp(:,i) = [];
                if mod(i,2) == 0
                    cof = 1;
                else
                    cof = -1;
                end
                out = out + cof*in(1,i)*determinate(inp);
            end
        else
            out = in;
        end
    else
        out = 'Not a Square Matrix.';
    end
end