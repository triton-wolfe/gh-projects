ear = listener(










function varargout = addFunc(in)
    persistent count
    switch in
        case 'add'
            count = count + 1;
        case 'reset'
            count = 0;
        case 'get'
            varargout{1} = count;
    end
end