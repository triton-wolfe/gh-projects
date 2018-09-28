function xls2mat(file)
    [~,~,ca] = xlsread(file);
    st = cell2struct(ca(2:end,:),ca(1,:),2);
    save('data.mat','st');
end