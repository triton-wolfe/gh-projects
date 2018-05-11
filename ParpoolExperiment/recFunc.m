function in = recFunc(in)
    if in > 1
        in = in * recFunc(in - 1);
    end
end