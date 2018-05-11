function num = timingStuff(dpt,itt,num)
    for i = 1:itt
        if dpt > 0
            num = timingStuff(dpt-1,itt,num+1);
        else
            num = num + 1;
        end
    end
end