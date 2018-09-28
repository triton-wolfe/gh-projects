function out = recFact(n)
    if n == 1 || n == 0
        out = 1;
    else
        out = n.*recFact(n-1);
    end
end