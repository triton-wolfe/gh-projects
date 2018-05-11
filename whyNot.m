%% whyNot: find largest multiple of 3digit numbers that is a palindrome
%
%
%
%%% Remarks


vec = [];
for i = 100:999
    for j = 100:999
        str = num2str(i*j);
        if isequal(str,str(end:-1:1))
            vec = [vec i*j];
        end
    end
end
num = vec(end)