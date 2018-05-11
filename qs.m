function s = qs(s)
    if length(s) <= 1
        s = [qs(s(s < median(s))) s(s == median(s)) qs(s(s > median(s)))];
    end
end