USE [master]
GO

ALTER DATABASE [competition-storage] SET ALLOW_SNAPSHOT_ISOLATION ON;
GO
  
ALTER DATABASE [competition-storage] SET READ_COMMITTED_SNAPSHOT ON;
GO


USE [competition-storage]
GO

DROP TABLE [dbo].[attachment_data_entity];
GO

CREATE TABLE [dbo].[attachment_data_entity](
  [id] [varchar](255) NOT NULL,
  [data] [varbinary](MAX) NOT NULL
  PRIMARY KEY CLUSTERED
  (
    [id] ASC
  ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
